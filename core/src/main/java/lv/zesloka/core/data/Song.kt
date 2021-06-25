package lv.zesloka.core.data


data class Song(
    val title: String = DEF_TITLE,
    val content: String = DEF_CONTENT,
    val creationTime: Long = DEF_CREATION_TIME,
    val updateTime: Long = DEF_UPDATE_TIME,
    val id: Long = DEF_ID
) {
    companion object {
        const val DEF_TITLE = ""
        const val DEF_CONTENT = ""
        const val DEF_CREATION_TIME = 0L
        const val DEF_UPDATE_TIME = 0L
        const val DEF_ID = 0L
    }
}

