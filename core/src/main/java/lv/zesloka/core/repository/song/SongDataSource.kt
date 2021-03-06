package lv.zesloka.core.repository.song

import lv.zesloka.core.data.Song

interface SongDataSource {
    suspend fun add(song: Song)

    suspend fun update(song: Song)

    suspend fun getById(id: Long): Song?

    suspend fun getAll(): List<Song>

    suspend fun delete(song: Song)
}