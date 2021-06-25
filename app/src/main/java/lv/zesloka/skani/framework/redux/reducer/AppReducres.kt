package lv.zesloka.skani.framework.redux.reducer

import lv.zesloka.skani.framework.redux.state.AppState
import lv.zesloka.skani.framework.redux.state.SongDetailsState

fun appReducer(state: AppState, action: Any): AppState {
    return AppState(
        songDetailsState = songDetailsReducer(
            SongDetailsState.selectFrom(state), action
        )
    )
}