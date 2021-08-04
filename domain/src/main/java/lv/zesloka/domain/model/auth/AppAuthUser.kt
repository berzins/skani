package lv.zesloka.domain.model.auth

data class AppAuthUser(val userId: String, val username: String) {
    companion object
}

fun AppAuthUser.Companion.empty() = AppAuthUser("","")