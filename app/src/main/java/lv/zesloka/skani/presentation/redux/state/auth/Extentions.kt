package lv.zesloka.skani.presentation.redux.state.auth

import lv.zesloka.domain.model.auth.AuthActoinDeliveryType
import lv.zesloka.domain.model.auth.signup.AuthSignUp
import lv.zesloka.domain.model.auth.signup.NextSignUpAction
import lv.zesloka.domain.usecase.base.ErrorCode
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.error.RdxError
import lv.zesloka.skani.presentation.redux.state.error.initial

/*  INITIAL */

fun RdxInputField.Companion.initialString(): RdxInputField<String> =
    RdxInputField("",false, ErrorCode.NONE)

fun RdxSignInState.Companion.initial(): RdxSignInState =
    RdxSignInState(
        isSignInInProgress = false,
        input = RdxSignInInput.initial(),
        error = RdxError.initial()
    )

fun RdxSignInInput.Companion.initial(): RdxSignInInput =
    RdxSignInInput(
        username = RdxInputField.initialString(),
        password = RdxInputField.initialString()
    )

fun RdxRegistration.Companion.initial(): RdxRegistration =
    RdxRegistration(
        isInLoadingState = false,
        isCompleted = false,
        input = RdxRegistrationInput.initial(),
        nextStep = RdxNextSignUpStep.initial(),
        signUpStage = RdxSignUpStage.SIGN_UP,
        error = RdxError.initial()
    )

fun RdxAuthState.Companion.initial(): RdxAuthState =
    RdxAuthState(
        signIn = RdxSignInState.initial(),
        registration = RdxRegistration.initial()
    )

fun RdxRegistrationInput.Companion.initial() =
    RdxRegistrationInput(
        username = RdxInputField.initialString(),
        email = RdxInputField.initialString(),
        password = RdxInputField.initialString()
    )

fun RdxNextSignUpStep.Companion.initial() =
    RdxNextSignUpStep(
        action = RdxNextSignUpAction.NONE,
        deliveryType = RdxAuthActionDeliveryType.NONE,
        user = RdxSignUpUser.initial()
    )

fun RdxSignUpUser.Companion.initial() =
    RdxSignUpUser(id = "", username = "")


/*  SELECTORS  */

fun RdxAuthState.Companion.selectFrom(state: RdxAppState) = state.authState

fun RdxRegistration.Companion.selectFrom(state: RdxAppState) =
    RdxAuthState.selectFrom(state).registration

fun RdxSignInState.Companion.selectFrom(state: RdxAppState) =
    RdxAuthState.selectFrom(state).signIn


/* MAPPERS */

fun RdxNextSignUpStep.Companion.fromSuccess(signUpResult: AuthSignUp): RdxNextSignUpStep {
    return RdxNextSignUpStep(
        user = RdxSignUpUser(signUpResult.user.userId, signUpResult.user.username),
        action = signUpResult.nextSignUpStep.action.toRdxSignUpAction(),
        deliveryType = signUpResult.nextSignUpStep.deliveryType.toRdxAuthActionDeliveryType()
    )
}

fun NextSignUpAction.toRdxSignUpAction(): RdxNextSignUpAction =
    when(this) {
        NextSignUpAction.CONFIRM_SIGN_UP_STEP -> RdxNextSignUpAction.CONFIRM_SIGN_UP_STEP
        NextSignUpAction.DONE -> RdxNextSignUpAction.DONE
        else -> RdxNextSignUpAction.NONE
    }

fun AuthActoinDeliveryType.toRdxAuthActionDeliveryType(): RdxAuthActionDeliveryType =
    when(this) {
        AuthActoinDeliveryType.EMAIL -> RdxAuthActionDeliveryType.EMAIL
        AuthActoinDeliveryType.PHONE -> RdxAuthActionDeliveryType.PHONE
        AuthActoinDeliveryType.SMS -> RdxAuthActionDeliveryType.SMS
        AuthActoinDeliveryType.UNKNOWN -> RdxAuthActionDeliveryType.UNKNOWN
        AuthActoinDeliveryType.NONE -> RdxAuthActionDeliveryType.NONE
    }