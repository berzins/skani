package lv.zesloka.skani.presentation.redux.reducer

import lv.zesloka.skani.presentation.redux.action.SetCurrentSong
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.song.RdxSongDetailsState
import lv.zesloka.skani.presentation.redux.state.song.selectFrom

fun songDetailsReducer(state: RdxAppState, action: Any): RdxSongDetailsState {
    val songDetailsState = RdxSongDetailsState.selectFrom(state)
    when (action) {
        is SetCurrentSong -> {
            return songDetailsState.copy(currentSong = action.song)
        }
    }
    return songDetailsState
}