package lv.zesloka.core.usecase.song

import lv.zesloka.core.data.Song
import lv.zesloka.core.repository.song.SongRepository

class UpdateSong constructor(private val repo: SongRepository) {
    suspend operator fun invoke(song: Song) = repo.update(song)
}