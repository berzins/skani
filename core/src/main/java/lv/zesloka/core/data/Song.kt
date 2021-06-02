package lv.zesloka.core.data

data class Song(
    val title: String,
    val content: SongContent,
    val creationTime: Long,
    val updateTime: Long,
    var id: Long
)

class SongContent