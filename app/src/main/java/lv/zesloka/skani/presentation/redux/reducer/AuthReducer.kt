package lv.zesloka.skani.presentation.redux.reducer

import lv.zesloka.domain.model.User
import lv.zesloka.domain.model.auth.AuthActoinDeliveryType
import lv.zesloka.domain.model.auth.AuthSignUp
import lv.zesloka.domain.model.auth.NextSignUpAction
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.auth.*

fun authReducer(state: RdxAppState, action: Any): RdxAuthState {

    val authState = RdxAuthState.selectFrom(state)
    val regState = RdxRegistration.selectFrom(state)
    when (action) {
        is UserActions.Register.SignUp.Success ->
            return authState.copy(
                registration = regState.copy(
                    nextStep = RdxNextSignUpStep.from(action.signUp),
                    signUpStage = action.signUp.toSignUpStage()
                )
            )

        is UserActions.Register.Complete ->
            return authState.copy(
                registration = regState.copy(
                    isCompleted = true,
                    nextStep = RdxNextSignUpStep.initial(),
                    signUpStage = RdxSignUpStage.SIGN_UP
                )
            )

        is UserActions.Register.VerifyEmail.Success ->
            return authState.copy(
                registration = regState.copy(
                    signUpStage = action.signUp.toSignUpStage()
                )
            )



//            return authState.copy(
//                registration = authState.registration.copy(
//                    isEmailVerifyActive = true
//                )
//            )
//        is ValidationActions.Auth.Username.Success ->
//            return authState.copy(
//                registration = authState.registration.copy(
//                    username = authState.registration.username.copy(
//                        value = action.username,
//                        isValid = true
//                    )
//                )
//            )
//        is ValidationActions.Auth.Email.Success ->
//            return authState.copy(
//                registration = authState.registration.copy(
//                    username = authState.registration.email.copy(
//                        value = action.email,
//                        isValid = true
//                    )
//                )
//            )
//        is ValidationActions.Auth.Password.Success ->
//            return authState.copy(
//                registration = authState.registration.copy(
//                    username = authState.registration.password.copy(
//                        value = action.password,
//                        isValid = true
//                    )
//                )
//            )
        else -> return authState

    }
}

fun AuthSignUp.toSignUpStage(): RdxSignUpStage =
    when {
        this.nextSignUpStep.action == NextSignUpAction.CONFIRM_SIGN_UP_STEP &&
                this.nextSignUpStep.deliveryType == AuthActoinDeliveryType.EMAIL ->
            RdxSignUpStage.VERIFY_EMAIL
        this.nextSignUpStep.action == NextSignUpAction.DONE ->
            RdxSignUpStage.DONE
        else -> RdxSignUpStage.SIGN_UP
    }