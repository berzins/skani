package lv.zesloka.skani.presentation.redux.reducer

import lv.zesloka.domain.model.auth.AuthActoinDeliveryType
import lv.zesloka.domain.model.auth.signup.AuthSignUp
import lv.zesloka.domain.model.auth.signup.NextSignUpAction
import lv.zesloka.domain.usecase.base.ErrorCode
import lv.zesloka.skani.presentation.model.navigation.Screen
import lv.zesloka.skani.presentation.redux.action.NavigationActions
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.action.ValidationActions
import lv.zesloka.skani.presentation.redux.action.ValidationActions.Field
import lv.zesloka.skani.presentation.redux.action.ValidationActions.Target
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.auth.*
import lv.zesloka.skani.presentation.redux.state.error.new

fun authReducer(state: RdxAppState, action: Any): RdxAuthState {

    val authState = RdxAuthState.selectFrom(state)
    val regState = RdxRegistration.selectFrom(state)
    val signInstate = RdxSignInState.selectFrom(state)

    when (action) {
        is NavigationActions.NavigationResult ->
            if (action.isSuccess) {
                if (action.screen == Screen.REGISTER) {
                    return authState.copy(
                        registration = RdxRegistration.initial()
                    )
                } else if (action.screen == Screen.LOGIN && regState.isCompleted) {
                    return authState.copy(
                        signIn = signInstate.copy(
                            input = signInstate.input.copy(
                                username = regState.input.username,
                                password = regState.input.password
                            )
                        )
                    )
                }
            }

        is UserActions.Register.SignUp.Start ->
            return authState.copy(
                registration = regState.copy(
                    isInLoadingState = true
                )
            )

        is UserActions.Register.SignUp.Success ->
            return authState.copy(
                registration = regState.copy(
                    isInLoadingState = false,
                    nextStep = RdxNextSignUpStep.fromSuccess(action.signUp),
                    signUpStage = action.signUp.toSignUpStage()
                )
            )

        is UserActions.Register.SignUp.Error ->
            return authState.copy(
                registration = regState.copy(
                    isInLoadingState = false,
                    error = regState.error.new(action.errorCode)
                )
            )

        is UserActions.Register.Complete ->
            return authState.copy(
                registration = regState.copy(
                    isInLoadingState = false,
                    isCompleted = true,
                    nextStep = RdxNextSignUpStep.initial(),
                    signUpStage = RdxSignUpStage.SIGN_UP
                )
            )
        is UserActions.Register.VerifyEmail.Start ->
            return authState.copy(
                registration = regState.copy(
                    isInLoadingState = true
                )
            )

        is UserActions.Register.VerifyEmail.Success ->
            return authState.copy(
                registration = regState.copy(
                    isInLoadingState = false,
                    signUpStage = action.signUp.toSignUpStage()
                )
            )
        is UserActions.Register.VerifyEmail.Error -> {
            return authState.copy(
                registration = regState.copy(
                    isInLoadingState = false,
                    error = regState.error.new(action.errorCode)
                )
            )
        }

        is UserActions.SignIn.Start -> {
            return authState.copy(
                signIn = signInstate.copy(
                    isSignInInProgress = true,
                    error = signInstate.error.new(ErrorCode.NONE)
                )
            )
        }

        is UserActions.SignIn.Success -> {
            return authState.copy(
                signIn = signInstate.copy(
                    isSignInInProgress = false,
                    input = RdxSignInInput.initial(),
                    error = signInstate.error.new(ErrorCode.NONE)
                )
            )
        }

        is UserActions.SignIn.Error -> {
            return authState.copy(
                signIn = signInstate.copy(
                    isSignInInProgress = false,
                    error = signInstate.error.new(action.errorCode)
                )
            )
        }

        is ValidationActions.String.Success -> {
            when (action.target) {
                Target.SIGN_UP -> {
                    return authState.copy(
                        registration = regState.copy(
                            input = regState.input.fromSuccess(action)
                        )
                    )
                }
            }
        }

        is ValidationActions.String.Error -> {
            when (action.target) {
                Target.SIGN_UP -> {
                    return authState.copy(
                        registration = regState.copy(
                            input = regState.input.fromError(action)
                        )
                    )
                }
            }
        }
    }
    return authState
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

fun RdxRegistrationInput.fromSuccess(success: ValidationActions.String.Success): RdxRegistrationInput =
    when (success.field) {
        Field.USERNAME -> this.copy(username = this.username.fromSuccess(success))
        Field.EMAIL -> this.copy(email = this.email.fromSuccess(success))
        Field.PASSWORD -> this.copy(password = this.password.fromSuccess(success))
        else -> this
    }

fun RdxRegistrationInput.fromError(error: ValidationActions.String.Error): RdxRegistrationInput =
    when (error.field) {
        Field.USERNAME -> this.copy(username = this.username.fromError(error))
        Field.EMAIL -> this.copy(email = this.email.fromError(error))
        Field.PASSWORD -> this.copy(password = this.password.fromError(error))
        else -> this
    }

fun RdxInputField<String>.fromSuccess(success: ValidationActions.String.Success): RdxInputField<String> =
    RdxInputField(value = success.value, isValid = true, errorCode = ErrorCode.NONE)

fun RdxInputField<String>.fromError(error: ValidationActions.String.Error): RdxInputField<String> =
    RdxInputField(value = error.value, isValid = false, errorCode = error.errorCode)