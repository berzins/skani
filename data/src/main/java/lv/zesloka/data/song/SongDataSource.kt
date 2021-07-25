package lv.zesloka.data.song

import lv.zesloka.domain.model.Song

interface SongDataSource  {

    suspend fun add(song: Song)

    suspend fun update(song: Song) = add(song)

    suspend fun getById(id: Long): Song?

    suspend fun getAll(): List<Song>

    suspend fun delete(song: Song)
}