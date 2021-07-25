package lv.zesloka.skani.presentation.redux.state

import lv.zesloka.skani.presentation.redux.state.song.RdxSongDetailsState
import lv.zesloka.skani.presentation.redux.state.user.RdxUserState


data class RdxAppState(
    val songDetailsState: RdxSongDetailsState,
    val userState: RdxUserState
) {
    companion object
}

