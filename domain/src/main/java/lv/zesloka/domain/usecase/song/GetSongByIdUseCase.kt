package lv.zesloka.domain.usecase.song

import lv.zesloka.domain.contract.song.SongRepo
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.Song
import lv.zesloka.domain.usecase.base.AbstractAsyncResultUseCase


class GetSongByIdUseCase constructor(repo: SongRepo):
    AbstractAsyncResultUseCase<GetSongByIdUseCase.Input, Song>() {

    class Input

    override suspend fun act(input: Input): Result<Song> {
        TODO("Not yet implemented")
    }
}