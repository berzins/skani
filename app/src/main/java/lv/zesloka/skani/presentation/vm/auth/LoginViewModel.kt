package lv.zesloka.skani.presentation.vm.auth

import androidx.lifecycle.ViewModel
import lv.zesloka.skani.di.qualifiyer.QLoginStoreSubscriber
import lv.zesloka.skani.presentation.model.navigation.Screen
import lv.zesloka.skani.presentation.redux.ActionDispatcher
import lv.zesloka.skani.presentation.redux.AppStoreSubscriber
import lv.zesloka.skani.presentation.redux.action.NavigationActions
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import javax.inject.Inject

open class LoginViewModel: ViewModel() {

    @Inject
    @QLoginStoreSubscriber
    protected lateinit var storeSubscriber: AppStoreSubscriber

    @Inject
    protected lateinit var dispatcher: ActionDispatcher

    fun init() {
        storeSubscriber.onRender { render(it) }
    }

    fun onRegisterAction() {
        dispatcher.dispatch(NavigationActions.StartNavigation(Screen.REGISTER))
    }

    private fun render(state: RdxAppState) {

    }

    fun onLoginAction(username: String, password: String) {
        dispatcher.dispatch(UserActions.SignIn.Start(
            username = username,
            password = password
        ))
    }

}