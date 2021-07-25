package lv.zesloka.skani.presentation.redux.state.extentions

import lv.zesloka.domain.model.Song
import lv.zesloka.skani.presentation.redux.state.song.RdxSong

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