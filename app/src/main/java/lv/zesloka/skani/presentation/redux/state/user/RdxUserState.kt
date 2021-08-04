package lv.zesloka.skani.presentation.redux.state.user

data class RdxUserState(val user: RdxUser, val isLoggedIn: Boolean, val lastError: RdxUserError?) {
    companion object{}

    data class RdxUserError(val code: Int, val message: String)
}

fun RdxUserState.hasNoError(): Boolean = lastError == null


