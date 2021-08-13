package lv.zesloka.skani.presentation.redux.middlware


import lv.zesloka.skani.presentation.redux.action.ValidationActions
import lv.zesloka.skani.presentation.redux.middlware.thunk.ValidateEmailThunk
import lv.zesloka.skani.presentation.redux.middlware.thunk.ValidatePasswordThunk
import lv.zesloka.skani.presentation.redux.middlware.thunk.ValidateUsernameThunk
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.Middleware
import org.reduxkotlin.middleware

fun validationMiddleware(
    validateUsernameThunk: ValidateUsernameThunk,
    validatePasswordThunk: ValidatePasswordThunk,
    validateEmailThunk: ValidateEmailThunk
): Middleware<RdxAppState> = middleware { store, next, action ->
    val result = next(action)
    val dispatch = store.dispatch
    when (action) {
        is ValidationActions.String.Validate -> {
            when (action.field) {
                ValidationActions.Field.USERNAME -> dispatch(validateUsernameThunk.validate(action))
                ValidationActions.Field.EMAIL -> dispatch(validateEmailThunk.validate(action))
                ValidationActions.Field.PASSWORD -> dispatch(validatePasswordThunk.validate(action))
                else -> dispatch(ValidationActions.UnsupportedRequest(action))
            }
        }
    }
    result
}
