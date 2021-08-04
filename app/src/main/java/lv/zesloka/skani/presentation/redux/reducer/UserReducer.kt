package lv.zesloka.skani.presentation.redux.reducer

import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.user.RdxUserState
import lv.zesloka.skani.presentation.redux.state.user.selectFrom

fun userReducer(state: RdxAppState, action: Any): RdxUserState {

    var userState = RdxUserState.selectFrom(state)

    when(action) {
        is UserActions.UserStateSuccess -> {
            return userState.copy(isLoggedIn = action.isSignedIn)
        }
        is UserActions.UserStateError -> {
            return userState.copy(lastError = RdxUserState.RdxUserError(action.code, action.message))
        }
    }

    return userState
}