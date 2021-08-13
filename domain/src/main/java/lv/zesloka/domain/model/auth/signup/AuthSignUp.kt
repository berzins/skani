package lv.zesloka.domain.model.auth.signup

import lv.zesloka.domain.model.auth.AppAuthUser

data class AuthSignUp(
    val isSignUpComplete: Boolean,
    val user: AppAuthUser,
    val nextSignUpStep: AuthNextSignUpStep
) {
    companion object
}