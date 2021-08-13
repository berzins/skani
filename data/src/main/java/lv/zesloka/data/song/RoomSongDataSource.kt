package lv.zesloka.data.song
//
//import android.content.Context
//import lv.zesloka.data.song.db.DatabaseService
//import lv.zesloka.data.song.db.SongEntity
//import lv.zesloka.domain.model.Song

//class RoomSongDataSource(context: Context): SongDataSource {
//
//    private val songDao = DatabaseService.getInstance(context).songDao()
//
//    override suspend fun add(song: Song) =
//        songDao.addSongEntity(SongEntity.fromSong(song))
//
//    override suspend fun update(song: Song) = add(song)
//
//    override suspend fun getById(id: Long): Song? =
//        songDao.getSongEntity(id)?.toSong()
//
//    override suspend fun getAll(): List<Song> =
//        songDao.getAllSongEntities().map { it.toSong() }
//
//    override suspend fun delete(song: Song) =
//        songDao.deleteSongEntity(SongEntity.fromSong(song))
//}