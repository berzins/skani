package lv.zesloka.domain.model.auth

data class AuthSignUp(
    val isSignUpComplete: Boolean,
    val user: AppAuthUser,
    val nextSignUpStep: AuthNextSignUpStep
) {
    companion object
}