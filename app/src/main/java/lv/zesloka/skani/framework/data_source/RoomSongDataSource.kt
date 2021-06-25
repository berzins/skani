package lv.zesloka.skani.framework.data_source

import android.content.Context
import lv.zesloka.core.data.Song
import lv.zesloka.core.repository.song.SongDataSource
import lv.zesloka.skani.framework.db.DatabaseService
import lv.zesloka.skani.framework.db.SongEntity

class RoomSongDataSource(context: Context): SongDataSource {

    private val songDao = DatabaseService.getInstance(context).songDao()

    override suspend fun add(song: Song) =
        songDao.addSongEntity(SongEntity.fromSong(song))

    override suspend fun update(song: Song) = add(song)

    override suspend fun getById(id: Long): Song? =
        songDao.getSongEntity(id)?.toSong()

    override suspend fun getAll(): List<Song> =
        songDao.getAllSongEntities().map { it.toSong() }

    override suspend fun delete(song: Song) =
        songDao.deleteSongEntity(SongEntity.fromSong(song))
}