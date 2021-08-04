package lv.zesloka.skani.presentation.redux.state.app

import lv.zesloka.skani.presentation.redux.state.navigatoin.RdxNavigationState
import lv.zesloka.skani.presentation.redux.state.auth.RdxAuthState
import lv.zesloka.skani.presentation.redux.state.song.RdxSongDetailsState
import lv.zesloka.skani.presentation.redux.state.user.RdxUserState


data class RdxAppState(
    val songDetailsState: RdxSongDetailsState,
    val userState: RdxUserState,
    val navigationState: RdxNavigationState,
    val authState: RdxAuthState
) {
    companion object
}

