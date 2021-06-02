package lv.zesloka.core.usecase.song

import lv.zesloka.core.repository.song.SongRepository

class GetAllSongs(private val repo: SongRepository) {
    suspend operator fun invoke() = repo.getAll()
}