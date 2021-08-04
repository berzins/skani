package lv.zesloka.skani.presentation.redux.state.app

import lv.zesloka.skani.presentation.redux.state.navigatoin.RdxNavigationState
import lv.zesloka.skani.presentation.redux.state.auth.RdxAuthState
import lv.zesloka.skani.presentation.redux.state.auth.initial
import lv.zesloka.skani.presentation.redux.state.navigatoin.initial
import lv.zesloka.skani.presentation.redux.state.song.RdxSongDetailsState
import lv.zesloka.skani.presentation.redux.state.song.initial
import lv.zesloka.skani.presentation.redux.state.user.RdxUserState
import lv.zesloka.skani.presentation.redux.state.user.initial

fun RdxAppState.Companion.initial(): RdxAppState =
    RdxAppState(
        songDetailsState = RdxSongDetailsState.initial(),
        userState = RdxUserState.initial(),
        navigationState = RdxNavigationState.initial(),
        authState = RdxAuthState.initial()
    )