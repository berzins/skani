package lv.zesloka.skani.framework.redux.action

import lv.zesloka.core.data.Song

data class SetCurrentSong(val song: Song)

data class SaveSong(val song: Song)

data class SongSaved(val song: Song)

data class DeleteSong(val song: Song)

data class SongDeleted(val song: Song)

class GetAllSongs()