package lv.zesloka.skani.presentation.redux.state.auth

import lv.zesloka.skani.presentation.redux.state.error.RdxError

data class RdxAuthState(
    val signIn: RdxSignInState,
    val registration: RdxRegistration
) {
    companion object
}

data class RdxRegistration(
    val isInLoadingState: Boolean,
    val isCompleted: Boolean,
    val input: RdxRegistrationInput,
    val nextStep: RdxNextSignUpStep,
    val signUpStage: RdxSignUpStage = RdxSignUpStage.SIGN_UP,
    val error: RdxError
) {
    companion object
}

data class RdxSignInState(
    val isSignInInProgress: Boolean,
    val input: RdxSignInInput,
    val error: RdxError
) {
    companion object
}

data class RdxSignInInput(val username: RdxInputField<String>, val password: RdxInputField<String>) {
    companion object
}

data class RdxInputField<T>(val value: T, val isValid: Boolean, val errorCode: Int) {
    companion object
}

data class RdxRegistrationInput(
    val username: RdxInputField<String>,
    val email: RdxInputField<String>,
    val password: RdxInputField<String>
) {
    companion object
}

data class RdxSignUpUser(val id: String, val username: String) {
    companion object
}

data class RdxNextSignUpStep(
    val user: RdxSignUpUser,
    val action: RdxNextSignUpAction,
    val deliveryType: RdxAuthActionDeliveryType
) {
    companion object
}

enum class RdxNextSignUpAction {
    CONFIRM_SIGN_UP_STEP,
    DONE,
    NONE
}

enum class RdxAuthActionDeliveryType {
    EMAIL,
    SMS,
    PHONE,
    UNKNOWN,
    NONE
}


enum class RdxSignUpStage {
    SIGN_UP,
    VERIFY_EMAIL,
    DONE
}



