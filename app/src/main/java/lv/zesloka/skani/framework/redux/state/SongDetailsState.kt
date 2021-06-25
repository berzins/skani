package lv.zesloka.skani.framework.redux.state

import lv.zesloka.core.data.Song

data class SongDetailsState(
    val currentSong: Song = Song()
) {
    companion object {
        fun selectFrom(state: AppState) = state.songDetailsState
    }
}