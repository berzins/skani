package lv.zesloka.skani.presentation.redux.state.user

data class RdxUser(
    val id: Long,
    val email: String,
    val nickname: String
) {
    fun isValid() = id > 0 && email.isNotBlank()

    companion object
}

