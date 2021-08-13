package lv.zesloka.domain.model.auth.signin

class AuthSignIn(
    val isSignInComplete: Boolean,
    val nextStep: AuthSignInNextStep
) {
    companion object
}

