package lv.zesloka.skani.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lv.zesloka.skani.di.qualifiyer.QDefaultInitText
import lv.zesloka.skani.di.qualifiyer.QSplashStoreSubscriber
import lv.zesloka.skani.di.qualifiyer.QSplashNavigator
import lv.zesloka.skani.presentation.model.navigation.ScreenNavigator
import lv.zesloka.skani.presentation.redux.ActionDispatcher
import lv.zesloka.skani.presentation.redux.AppStoreSubscriber
import lv.zesloka.skani.presentation.redux.action.InitActions
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.user.RdxUserState
import lv.zesloka.skani.presentation.redux.state.user.hasNoError
import lv.zesloka.skani.presentation.redux.state.user.selectFrom
import lv.zesloka.skani.presentation.vm.contract.StringResolver

import javax.inject.Inject

open class SplashViewModel : ViewModel() {

    @Inject
    protected lateinit var dispatcher: ActionDispatcher

    @Inject
    @QSplashStoreSubscriber
    protected lateinit var storeSubscriber: AppStoreSubscriber

    @Inject
    @QDefaultInitText
    protected lateinit var defaultInitText: StringResolver

    private val initInfo: MutableLiveData<String> = MutableLiveData()
    private val initError: MutableLiveData<String> = MutableLiveData()
    private val isErrorPresent: MutableLiveData<Boolean> = MutableLiveData()

    fun init() {
        storeSubscriber.onRender { state -> render(state) }
        dispatcher.dispatch(InitActions.OnStart.Init())
        showInitInfo(defaultInitText.get())
    }

    private fun showInitInfo(info: String) {
        initInfo.postValue(info)
        isErrorPresent.postValue(false)
    }

    private fun showInitError(error: String) {
        initError.postValue(error)
        isErrorPresent.postValue(true)
    }

    fun getInitInfo(): LiveData<String> = initInfo

    fun getInitError(): LiveData<String> = initError

    fun isInitErrorPresent(): LiveData<Boolean> = isErrorPresent

    private fun render(state: RdxAppState) {
        val userState = RdxUserState.selectFrom(state)

        if (userState.hasNoError()) {
            if (userState.isLoggedIn) {
                showInitInfo("User logged in")
            } else {
                showInitInfo("User not logged in")
            }
        } else {
            showInitError(userState.lastError?.message ?: "No error message")
        }
    }

}

