package lv.zesloka.skani.presentation.redux.middlware

import lv.zesloka.skani.presentation.model.navigation.Screen
import lv.zesloka.skani.presentation.redux.action.NavigationActions
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.middlware.thunk.NavigationThunks
import lv.zesloka.skani.presentation.redux.middlware.thunk.UserThunks
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.middleware

fun userActionMiddleware(userThunks: UserThunks) = middleware<RdxAppState> { store, next, action ->
    val dispatch = store.dispatch
    val result = next(action)
    when (action) {
        is UserActions.GetUserStatus -> dispatch(userThunks.fetchUserStatus())
        is UserActions.Register.SignUp.Start ->
            dispatch(userThunks.registerUser(action.username, action.email, action.password))
        is UserActions.Register.VerifyEmail.Start ->
            dispatch(userThunks.verifyEmail(
                username = action.username,
                code = action.code
            ))
        is UserActions.Register.Complete ->
            dispatch(NavigationActions.StartNavigation(Screen.LOGIN))
        is UserActions.SignIn.Start ->
            dispatch(userThunks.signIn(action.username, action.password))
    }
    result
}