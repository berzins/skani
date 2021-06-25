package lv.zesloka.skani.framework.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import lv.zesloka.core.data.Song

const val TABLE_NAME_SONG = "song"

@Entity(tableName = TABLE_NAME_SONG)
data class SongEntity(
    val title: String,
    @Embedded
    val content: String,
    @ColumnInfo(name = "creation_date")
    val creationTime: Long,
    @ColumnInfo(name = "update_time")
    val updateTime: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
) {
    companion object {
        fun fromSong(song: Song) = SongEntity(
            song.title,
            song.content,
            song.creationTime,
            song.updateTime
        )
    }

    fun toSong() = Song(title, content, creationTime, updateTime, id)
}
