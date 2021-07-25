package lv.zesloka.skani.presentation.redux.state.song

data class RdxSong(
    val title: String,
    val content: String,
    val creationTime: Long,
    val updateTime: Long,
    val id: Long = 0L
) {
    companion object
}