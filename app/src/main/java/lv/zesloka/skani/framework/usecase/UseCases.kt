package lv.zesloka.skani.framework.usecase

import lv.zesloka.core.usecase.song.*

data class UseCases(
    val addSong: AddSong,
    val deleteSong: DeleteSong,
    val getAllSongs: GetAllSongs,
    val getSongById: GetSongById,
    val updateSong: UpdateSong
)
