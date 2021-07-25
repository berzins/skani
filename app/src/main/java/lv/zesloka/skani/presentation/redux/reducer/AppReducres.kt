package lv.zesloka.skani.presentation.redux.reducer

import lv.zesloka.skani.presentation.redux.state.RdxAppState
import lv.zesloka.skani.presentation.redux.state.extentions.selectFrom
import lv.zesloka.skani.presentation.redux.state.song.RdxSongDetailsState
import lv.zesloka.skani.presentation.redux.state.user.RdxUserState

fun appReducer(state: RdxAppState, action: Any): RdxAppState {
    return RdxAppState(
        songDetailsState = songDetailsReducer(state, action),
        userState = userReducer(state, action)
    )
}