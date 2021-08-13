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
        /* USER STATUS */
        is UserActions.GetUserStatus -> dispatch(userThunks.fetchUserStatus())
        is UserActions.UserStateSuccess -> {
            if (action.isSignedIn) {
                dispatch(NavigationActions.StartNavigation(Screen.HOME))
            } else {
                dispatch(NavigationActions.StartNavigation(Screen.LOGIN))
            }
        }

        /* SIGN UP */
        is UserActions.Register.SignUp.Start ->
            dispatch(userThunks.registerUser(action.username, action.email, action.password))
        is UserActions.Register.VerifyEmail.Start ->
            dispatch(userThunks.verifyEmail(action.username, action.code))
        is UserActions.Register.Complete ->
            dispatch(NavigationActions.StartNavigation(Screen.LOGIN))

        /* SIGN IN */
        is UserActions.SignIn.Start ->
            dispatch(userThunks.signIn(action.username, action.password))
        is UserActions.SignIn.Success -> {
            dispatch(NavigationActions.StartNavigation(Screen.HOME))
        }

        /* SIGN OUT*/
        is UserActions.SignOut.Start -> dispatch(userThunks.signOut())
        is UserActions.SignOut.Success ->
            dispatch(NavigationActions.StartNavigation(Screen.LOGIN))
    }
    result
}