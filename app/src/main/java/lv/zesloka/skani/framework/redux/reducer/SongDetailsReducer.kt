package lv.zesloka.skani.framework.redux.reducer

import lv.zesloka.skani.framework.redux.action.SetCurrentSong
import lv.zesloka.skani.framework.redux.state.SongDetailsState

fun songDetailsReducer(state: SongDetailsState, action: Any): SongDetailsState {
    when (action) {
        is SetCurrentSong -> {
            return state.copy(currentSong = action.song)
        }
    }
    return state
}