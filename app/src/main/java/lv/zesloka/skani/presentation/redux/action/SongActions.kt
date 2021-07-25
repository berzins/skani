package lv.zesloka.skani.presentation.redux.action

import lv.zesloka.skani.presentation.redux.state.song.RdxSong


data class SetCurrentSong(val song: RdxSong)

data class SaveSong(val song: RdxSong)

data class SongSaved(val song: RdxSong)

data class DeleteSong(val song: RdxSong)

data class SongDeleted(val song: RdxSong)

class GetAllSongs()