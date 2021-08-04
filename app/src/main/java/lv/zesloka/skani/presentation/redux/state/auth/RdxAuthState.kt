package lv.zesloka.skani.presentation.redux.state.auth

data class RdxAuthState(
    val login: RdxLogin,
    val registration: RdxRegistration
) {
    companion object {}
}

data class RdxRegistration(
    val isCompleted: Boolean,
    val input: RdxRegistrationInput,
    val nextStep: RdxNextSignUpStep,
    val signUpStage: RdxSignUpStage = RdxSignUpStage.SIGN_UP
) {
    companion object {}
}

data class RdxLogin(
    val username: RdxInputField<String>,
    val password: RdxInputField<String>
) {
    companion object {}
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



