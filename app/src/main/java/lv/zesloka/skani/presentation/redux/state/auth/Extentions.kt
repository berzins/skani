package lv.zesloka.skani.presentation.redux.state.auth

import lv.zesloka.domain.model.auth.AuthActoinDeliveryType
import lv.zesloka.domain.model.auth.AuthSignUp
import lv.zesloka.domain.model.auth.NextSignUpAction
import lv.zesloka.domain.usecase.base.ErrorCodes
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState

/*  INITIAL */

fun RdxInputField.Companion.initialString(): RdxInputField<String> =
    RdxInputField("",false, ErrorCodes.NONE)

fun RdxLogin.Companion.initial(): RdxLogin =
    RdxLogin(
        username = RdxInputField.initialString(),
        password = RdxInputField.initialString()
    )

fun RdxRegistration.Companion.initial(): RdxRegistration =
    RdxRegistration(
        isCompleted = false,
        input = RdxRegistrationInput.initial(),
        nextStep = RdxNextSignUpStep.initial(),
        signUpStage = RdxSignUpStage.SIGN_UP
    )

fun RdxAuthState.Companion.initial(): RdxAuthState =
    RdxAuthState(
        login = RdxLogin.initial(),
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


/* MAPPERS */

fun RdxNextSignUpStep.Companion.from(signUpResult: AuthSignUp): RdxNextSignUpStep {
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