package lv.zesloka.skani.presentation.redux.reducer

import lv.zesloka.skani.presentation.redux.state.RdxAppState
import lv.zesloka.skani.presentation.redux.state.extentions.selectFrom
import lv.zesloka.skani.presentation.redux.state.user.RdxUserState

fun userReducer(state: RdxAppState, action: Any): RdxUserState {

    val userState = RdxUserState.selectFrom(state)

    // reducer logic here
    // ...

    return userState
}