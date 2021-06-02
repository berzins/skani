package lv.zesloka.core.usecase.song

import lv.zesloka.core.data.Song
import lv.zesloka.core.repository.song.SongRepository

class DeleteSong constructor(private val repo: SongRepository) {
    suspend operator fun invoke(song: Song) = repo.delete(song)
}