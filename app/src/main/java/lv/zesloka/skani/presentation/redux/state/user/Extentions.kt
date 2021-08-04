package lv.zesloka.skani.presentation.redux.state.user

import lv.zesloka.skani.presentation.Const
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState

fun RdxUser.Companion.initial() =
    RdxUser(
        id = Const.NOT_DEFINED_LONG,
        email = Const.NOT_DEFINED_STRING,
        nickname = Const.NOT_DEFINED_STRING
    )

fun RdxUserState.Companion.initial() = RdxUserState(
    user = RdxUser.initial(),
    isLoggedIn = false,
    lastError = null
)

fun RdxUserState.Companion.selectFrom(state: RdxAppState): RdxUserState = state.userState