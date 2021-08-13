package lv.zesloka.skani.presentation.vm.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lv.zesloka.domain.usecase.base.ErrorCode
import lv.zesloka.skani.di.qualifiyer.*
import lv.zesloka.skani.presentation.model.dialog.SingleActionDialog
import lv.zesloka.skani.presentation.model.dialog.genericError
import lv.zesloka.skani.presentation.model.navigation.Screen
import lv.zesloka.skani.presentation.model.text.AppStringId
import lv.zesloka.skani.presentation.redux.ActionDispatcher
import lv.zesloka.skani.presentation.redux.AppStoreSubscriber
import lv.zesloka.skani.presentation.redux.ErrorSubscriber
import lv.zesloka.skani.presentation.redux.action.NavigationActions
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.auth.RdxSignInState
import lv.zesloka.skani.presentation.redux.state.auth.selectFrom
import lv.zesloka.skani.presentation.vm.contract.ResolverProvider
import lv.zesloka.skani.ui.postIfNew
import javax.inject.Inject

open class LoginViewModel : ViewModel() {

    @Inject
    @QLoginStoreSubscriber
    protected lateinit var storeSubscriber: AppStoreSubscriber

    @Inject
    @QSignInErrorSubscriber
    protected lateinit var signInErrorSubscriber: ErrorSubscriber

    @Inject
    protected lateinit var dispatcher: ActionDispatcher

    @Inject
    protected lateinit var strings: ResolverProvider<String>

    private val _isSignInLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isSignInLoading: LiveData<Boolean>
        get() = _isSignInLoading

    private val _errorDialog: MutableLiveData<SingleActionDialog> = MutableLiveData()
    val errorDialog: LiveData<SingleActionDialog>
        get() = _errorDialog

    fun init() {
        storeSubscriber.onRender { render(it) }
        signInErrorSubscriber.doOnError { onError(it) }
    }

    private fun render(state: RdxAppState) {
        val signInState = RdxSignInState.selectFrom(state)
        _isSignInLoading.postIfNew(signInState.isSignInInProgress)
    }

    private fun onError(code: Int) {
        when (code) {
            ErrorCode.INVALID_CREDENTIALS -> _errorDialog.postValue(
                SingleActionDialog.invalidCredentialsDialog(strings)
            )
            else -> _errorDialog.postValue(
                SingleActionDialog.genericError(strings) {}
            )
        }
    }

    fun onRegisterAction() {
        dispatcher.dispatch(NavigationActions.StartNavigation(Screen.REGISTER))
    }

    fun onLoginAction(username: String, password: String) {
        dispatcher.dispatch(
            UserActions.SignIn.Start(
                username = username,
                password = password
            )
        )
    }

    override fun onCleared() {
        storeSubscriber.dispose()
        signInErrorSubscriber.dispose()
    }
}

private fun SingleActionDialog.Companion.invalidCredentialsDialog(
    strings: ResolverProvider<String>
) = SingleActionDialog(
    title = strings.get(AppStringId.TITLE_LOGIN).get(),
    message = strings.get(AppStringId.ERR_MSG_WRONG_USERNAME_OR_PASSWORD).get(),
    actionLabel = strings.get(AppStringId.LABEL_ACTION_POSITIVE).get(),
    action = {}
)