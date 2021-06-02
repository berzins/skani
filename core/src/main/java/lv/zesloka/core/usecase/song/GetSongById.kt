package lv.zesloka.core.usecase.song

import lv.zesloka.core.repository.song.SongRepository

class GetSongById(private val repo: SongRepository) {
    suspend operator fun invoke(id: Long) = repo.getById(id)
}