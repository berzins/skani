package lv.zesloka.skani.presentation.redux.state.song

import lv.zesloka.domain.model.Song
import lv.zesloka.skani.presentation.Const
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState

fun RdxSongDetailsState.Companion.initial(): RdxSongDetailsState =
    RdxSongDetailsState(
        currentSong = RdxSong.initial()
    )

fun RdxSong.Companion.initial(): RdxSong {
    return RdxSong(
        title = Const.NOT_DEFINED_STRING,
        content = Const.NOT_DEFINED_STRING,
        creationTime = Const.NOT_DEFINED_LONG,
        updateTime = Const.NOT_DEFINED_LONG
    )
}

fun RdxSongDetailsState.Companion.selectFrom(state: RdxAppState) : RdxSongDetailsState =
    state.songDetailsState

fun RdxSong.Companion.from(song: Song) = RdxSong(
    id = song.id,
    title = song.title,
    content = song.content,
    creationTime = song.creationTime,
    updateTime = song.updateTime
)

fun RdxSong.toSong() = Song(
    id = this.id,
    title = this.title,
    content = this.content,
    creationTime = this.creationTime,
    updateTime = this.updateTime
)