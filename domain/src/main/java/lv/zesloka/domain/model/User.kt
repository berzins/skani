package lv.zesloka.domain.model

data class User(
    val isSignedIn: Boolean
) {
    companion object {
        val UNKNOWN: User = User(isSignedIn = false)
    }
}