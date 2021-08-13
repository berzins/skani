package lv.zesloka.data.song.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query


//@Dao
//interface SongDao {
//    @Insert(onConflict = REPLACE)
//    suspend fun addSongEntity(songEntity: SongEntity)
//
//    @Query("SELECT * FROM $TABLE_NAME_SONG WHERE id = :id")
//    suspend fun getSongEntity(id: Long): SongEntity?
//
//    @Query("SELECT * FROM $TABLE_NAME_SONG")
//    suspend fun getAllSongEntities(): List<SongEntity>
//
//    @Delete
//    suspend fun deleteSongEntity(songEntity: SongEntity)
//}