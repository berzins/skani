package lv.zesloka.domain.usecase.song

import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.Song
import lv.zesloka.domain.usecase.base.AbstractAsyncResultUseCase


class GetAllSongsUseCase : AbstractAsyncResultUseCase<Unit, Song>() {

    override suspend fun act(input: Unit): Result<Song> {
        TODO("Not yet implemented")
    }
}
