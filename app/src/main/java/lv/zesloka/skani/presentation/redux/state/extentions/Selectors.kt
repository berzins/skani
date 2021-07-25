package lv.zesloka.skani.presentation.redux.state.extentions

import lv.zesloka.skani.presentation.redux.state.RdxAppState
import lv.zesloka.skani.presentation.redux.state.song.RdxSongDetailsState
import lv.zesloka.skani.presentation.redux.state.user.RdxUserState

fun RdxUserState.Companion.selectFrom(state: RdxAppState): RdxUserState = state.userState

fun RdxSongDetailsState.Companion.selectFrom(state: RdxAppState) : RdxSongDetailsState =
    state.songDetailsState
