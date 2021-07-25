package lv.zesloka.domain.model


data class Song(
    val title: String,
    val content: String,
    val creationTime: Long,
    val updateTime: Long,
    val id: Long = 0L
)