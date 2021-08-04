package lv.zesloka.skani.presentation.redux.middlware

import lv.zesloka.skani.presentation.redux.action.ValidationActions
import lv.zesloka.skani.presentation.redux.middlware.thunk.AuthInputValidationThunks
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.Middleware
import org.reduxkotlin.middleware


fun validationMiddleware(
    authInputValidationThunks: AuthInputValidationThunks
): Middleware<RdxAppState> = middleware { store, next, action ->
    val result = next(action)
    val dispatch = store.dispatch
    when (action) {
        is ValidationActions.Auth.Username.Validate ->
            dispatch(authInputValidationThunks.validateUsername(action.username))
        is ValidationActions.Auth.Email.Validate ->
            dispatch(authInputValidationThunks.validateEmail(action.email))
        is ValidationActions.Auth.Password.Validate ->
            dispatch(authInputValidationThunks.validatePassword(action.password))
    }
    result
}