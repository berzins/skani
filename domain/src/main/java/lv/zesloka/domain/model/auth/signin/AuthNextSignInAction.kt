package lv.zesloka.domain.model.auth.signin

enum class AuthNextSignInAction {
    CONFIRM_SIGN_IN_WITH_SMS_MFA_CODE,
    CONFIRM_SIGN_IN_WITH_CUSTOM_CHALLENGE,
    CONFIRM_SIGN_IN_WITH_NEW_PASSWORD,
    RESET_PASSWORD,
    CONFIRM_SIGN_UP,
    DONE
}