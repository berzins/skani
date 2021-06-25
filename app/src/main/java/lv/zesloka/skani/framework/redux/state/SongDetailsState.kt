package lv.zesloka.skani.framework.redux.state

import lv.zesloka.core.data.Song
import org.rekotlin.StateType

data class SongDetailsState(
    val currentSong: Song = Song()
): StateType {
    companion object {
        fun selectFrom(state: AppState) = state.songDetailsState
    }
}