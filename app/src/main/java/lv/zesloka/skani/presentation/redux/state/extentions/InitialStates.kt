package lv.zesloka.skani.presentation.redux.state.extentions


import lv.zesloka.skani.presentation.Const
import lv.zesloka.skani.presentation.redux.state.RdxAppState
import lv.zesloka.skani.presentation.redux.state.song.RdxSong
import lv.zesloka.skani.presentation.redux.state.song.RdxSongDetailsState
import lv.zesloka.skani.presentation.redux.state.user.RdxUser
import lv.zesloka.skani.presentation.redux.state.user.RdxUserState

// ---- Initial States -----


public fun RdxAppState.Companion.initial(): RdxAppState =
    RdxAppState(
        songDetailsState = RdxSongDetailsState.initial(),
        userState = RdxUserState.initial()
    )

public fun RdxSongDetailsState.Companion.initial(): RdxSongDetailsState =
    RdxSongDetailsState(
        currentSong = RdxSong.initial()
    )

public fun RdxSong.Companion.initial(): RdxSong {
    return RdxSong(
        title = Const.NOT_DEFINED_STRING,
        content = Const.NOT_DEFINED_STRING,
        creationTime = Const.NOT_DEFINED_LONG,
        updateTime = Const.NOT_DEFINED_LONG
    )
}

public fun RdxUser.Companion.initial() =
    RdxUser(
        id = Const.NOT_DEFINED_LONG,
        email = Const.NOT_DEFINED_STRING,
        nickname = Const.NOT_DEFINED_STRING
    )

public fun RdxUserState.Companion.initial() = RdxUserState(user = RdxUser.initial())


// ---- Selectors ----