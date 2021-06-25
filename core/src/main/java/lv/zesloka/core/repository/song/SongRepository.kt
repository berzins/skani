package lv.zesloka.core.repository.song

import lv.zesloka.core.data.Song

class SongRepository(private val source: SongDataSource) {
    suspend fun add(song: Song) = source.add(song)

    suspend fun update(song: Song) = source.update(song)

    suspend fun getById(id: Long) = source.getById(id)

    suspend fun getAll() = source.getAll()

    suspend fun delete(song: Song) = source.delete(song)

}