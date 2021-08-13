package lv.zesloka.skani.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lv.zesloka.domain.usecase.base.ErrorCode
import lv.zesloka.skani.di.qualifiyer.QSplashStoreSubscriber
import lv.zesloka.skani.presentation.model.ErrorMsg
import lv.zesloka.skani.presentation.model.text.AppStringId
import lv.zesloka.skani.presentation.redux.ActionDispatcher
import lv.zesloka.skani.presentation.redux.AppStoreSubscriber
import lv.zesloka.skani.presentation.redux.action.InitActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.user.RdxUserState
import lv.zesloka.skani.presentation.redux.state.user.hasNoError
import lv.zesloka.skani.presentation.redux.state.user.selectFrom

import javax.inject.Inject

open class SplashViewModel : ViewModel() {

    @Inject
    protected lateinit var dispatcher: ActionDispatcher

    @Inject
    @QSplashStoreSubscriber
    protected lateinit var storeSubscriber: AppStoreSubscriber

    private val initInfo: MutableLiveData<AppStringId> = MutableLiveData()
    private val initError: MutableLiveData<ErrorMsg> = MutableLiveData()
    private val isErrorPresent: MutableLiveData<Boolean> = MutableLiveData()

    fun init() {
        storeSubscriber.onRender { state -> render(state) }
        dispatcher.dispatch(InitActions.OnStart.Init())
        showInitInfo(AppStringId.TEXT_INITIALIZE)
    }

    private fun showInitInfo(info: AppStringId) {
        initInfo.postValue(info)
        isErrorPresent.postValue(false)
    }

    private fun showInitError(error: ErrorMsg) {
        initError.postValue(error)
        isErrorPresent.postValue(true)
    }

    fun getInitInfo(): LiveData<AppStringId> = initInfo

    fun getInitError(): LiveData<ErrorMsg> = initError

    fun isInitErrorPresent(): LiveData<Boolean> = isErrorPresent

    private fun render(state: RdxAppState) {
        val userState = RdxUserState.selectFrom(state)

        if (userState.hasNoError()) {
            if (userState.isLoggedIn) {
                showInitInfo(AppStringId.TEXT_USER_LODGED_IN)
            } else {
                showInitInfo(AppStringId.TEXT_USER_NOT_LODGED_IN)
            }
        } else {
            showInitError(
                ErrorMsg(
                    AppStringId.ERR_MSG_ERROR_HAPPENED,
                    userState.lastError?.code ?: ErrorCode.UNKNOWN
                )
            )
        }
    }

}

