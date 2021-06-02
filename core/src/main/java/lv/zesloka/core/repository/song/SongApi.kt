package lv.zesloka.core.repository.song

import lv.zesloka.core.data.Song

interface SongApi {
    suspend fun add(song: Song)

    suspend fun update(song: Song)

    suspend fun getSong(id: Long): Song?

    suspend fun getAll(): List<Song>

    suspend fun delete(song: Song)
}