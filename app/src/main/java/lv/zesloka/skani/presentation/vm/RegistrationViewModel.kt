package lv.zesloka.skani.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lv.zesloka.skani.presentation.model.navigation.Screen
import lv.zesloka.skani.presentation.redux.ActionDispatcher
import lv.zesloka.skani.presentation.redux.AppStateProvider
import lv.zesloka.skani.presentation.redux.AppStoreSubscriber
import lv.zesloka.skani.presentation.redux.action.NavigationActions
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.auth.RdxAuthState
import lv.zesloka.skani.presentation.redux.state.auth.RdxRegistration
import lv.zesloka.skani.presentation.redux.state.auth.RdxSignUpStage
import lv.zesloka.skani.presentation.redux.state.auth.selectFrom
import javax.inject.Inject

open class RegistrationViewModel : ViewModel() {

    @Inject
    protected lateinit var dispatcher: ActionDispatcher

    @Inject
    protected lateinit var storeSubscriber: AppStoreSubscriber

    @Inject
    lateinit var stateProvider: AppStateProvider<RdxAppState>

    private var stage: MutableLiveData<RdxSignUpStage> = MutableLiveData()

    fun getAuthStage(): LiveData<RdxSignUpStage> = stage

    fun init() {
        stage.postValue(RdxRegistration.selectFrom(stateProvider.getState()).signUpStage)
        storeSubscriber.onRender { render(it) }
    }

    fun onRegisterAction(username: String, email: String, password: String) {
        dispatcher.dispatch(UserActions.Register.SignUp.Start(username, email, password))
    }

    fun onBack() {
        dispatcher.dispatch(NavigationActions.StartNavigation(Screen.LOGIN))
    }

    private fun render(state: RdxAppState) {
        val authState = RdxAuthState.selectFrom(state)
        val signUpStage = authState.registration.signUpStage
        if (stage.value != signUpStage) {
            stage.postValue(signUpStage)
        }
    }

    fun onVerifyCode(code: String) {
        val regState = RdxRegistration.selectFrom(stateProvider.getState())
        dispatcher.dispatch(UserActions.Register.VerifyEmail.Start(
            username = regState.nextStep.user.username,
            code = code
        ))
    }

    fun onOnComplete() {
        dispatcher.dispatch(UserActions.Register.Complete())
    }

}