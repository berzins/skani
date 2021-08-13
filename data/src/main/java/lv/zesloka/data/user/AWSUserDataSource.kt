package lv.zesloka.data.user

import com.amazonaws.services.cognitoidentityprovider.model.CodeMismatchException
import com.amazonaws.services.cognitoidentityprovider.model.InvalidParameterException
import com.amplifyframework.auth.AuthCodeDeliveryDetails
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUser
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.auth.result.step.AuthSignInStep
import com.amplifyframework.auth.result.step.AuthSignUpStep
import kotlinx.coroutines.ExperimentalCoroutinesApi
import lv.zesloka.domain.model.User
import lv.zesloka.domain.model.auth.*
import lv.zesloka.domain.model.auth.signin.AuthNextSignInAction
import lv.zesloka.domain.model.auth.signin.AuthSignIn
import lv.zesloka.domain.model.auth.signin.AuthSignInNextStep
import lv.zesloka.domain.model.auth.signup.AuthNextSignUpStep
import lv.zesloka.domain.model.auth.signup.AuthSignUp
import lv.zesloka.domain.model.auth.signup.NextSignUpAction
import lv.zesloka.domain.model.exception.InvalidEmailVerifyCodeException
import lv.zesloka.domain.model.exception.InvalidUsernameOrPasswordException
import lv.zesloka.domain.model.exception.InvalidSignUpInputException

@ExperimentalCoroutinesApi
class AWSUserDataSource constructor(private val mUserManager: AWSUserManager) : UserDataSource {

    override suspend fun getUser(): User {
        return mUserManager.getUser()
    }

    override suspend fun signUpUser(
        username: String,
        email: String,
        password: String
    ): AuthSignUp {
        try {
            val result = mUserManager.registerUser(username, email, password)
            return AuthSignUp.from(result)
        } catch (e: InvalidParameterException) {
            throw InvalidSignUpInputException(e)
        }
    }

    override suspend fun verifyEmail(username: String, verifyCode: String): AuthSignUp {
        try {
            val result = mUserManager.verifyEmail(username, verifyCode)
            return AuthSignUp.from(result)
        } catch (e: CodeMismatchException) {
            throw InvalidEmailVerifyCodeException(e)
        } catch (e: AuthException.CodeMismatchException) {
            throw InvalidEmailVerifyCodeException(e)
        }
    }

    override suspend fun signIn(username: String, password: String): AuthSignIn {
        try {
            val result = mUserManager.signIn(username, password)
            return AuthSignIn.from(result)
        } catch (e: AuthException.UserNotFoundException) {
            throw InvalidUsernameOrPasswordException(e)
        } catch (e: AuthException) {
            throw InvalidUsernameOrPasswordException(e)
        }
    }

    override suspend fun signOut(): Boolean = mUserManager.signOut()

}

fun AuthSignUp.Companion.from(result: AuthSignUpResult) =
    AuthSignUp(
        isSignUpComplete = result.isSignUpComplete,
        user = AppAuthUser.from(result.user),
        nextSignUpStep = result.nextStep.toAuthNextStep()
    )

fun AuthUser.toAppAuthUser(): AppAuthUser =
    AppAuthUser(
        userId = this.userId,
        username = this.username
    )

fun AppAuthUser.Companion.from(awsUser: AuthUser?): AppAuthUser =
    awsUser?.toAppAuthUser() ?: AppAuthUser.empty()

fun com.amplifyframework.auth.result.step.AuthNextSignUpStep.toAuthNextStep(): AuthNextSignUpStep =
    AuthNextSignUpStep(
        action = this.signUpStep.toAuthSignUpStepAction(),
        deliveryType = this.codeDeliveryDetails?.toAuthCodeDeliveryType()
            ?: AuthActoinDeliveryType.NONE
    )


fun AuthSignUpStep.toAuthSignUpStepAction(): NextSignUpAction =
    when (this) {
        AuthSignUpStep.CONFIRM_SIGN_UP_STEP -> NextSignUpAction.CONFIRM_SIGN_UP_STEP
        AuthSignUpStep.DONE -> NextSignUpAction.DONE
    }

fun AuthCodeDeliveryDetails.toAuthCodeDeliveryType(): AuthActoinDeliveryType =
    when (this.deliveryMedium) {
        AuthCodeDeliveryDetails.DeliveryMedium.EMAIL -> AuthActoinDeliveryType.EMAIL
        AuthCodeDeliveryDetails.DeliveryMedium.PHONE -> AuthActoinDeliveryType.PHONE
        AuthCodeDeliveryDetails.DeliveryMedium.SMS -> AuthActoinDeliveryType.SMS
        AuthCodeDeliveryDetails.DeliveryMedium.UNKNOWN -> AuthActoinDeliveryType.UNKNOWN
    }

fun AuthSignIn.Companion.from(result: AuthSignInResult): AuthSignIn =
    AuthSignIn(
        isSignInComplete = result.isSignInComplete,
        nextStep = AuthSignInNextStep(
            nextSignInAction = result.nextStep.signInStep.toAuthSignAction(),
            actionDeliveryType = AuthActoinDeliveryType.NONE // todo: implement when something more expected here.. currently it's just null
        )
    )

fun AuthSignInStep.toAuthSignAction(): AuthNextSignInAction =
    when (this) {
        AuthSignInStep.DONE -> AuthNextSignInAction.DONE
        AuthSignInStep.CONFIRM_SIGN_IN_WITH_SMS_MFA_CODE ->
            AuthNextSignInAction.CONFIRM_SIGN_IN_WITH_SMS_MFA_CODE
        AuthSignInStep.CONFIRM_SIGN_IN_WITH_CUSTOM_CHALLENGE ->
            AuthNextSignInAction.CONFIRM_SIGN_IN_WITH_CUSTOM_CHALLENGE
        AuthSignInStep.CONFIRM_SIGN_IN_WITH_NEW_PASSWORD ->
            AuthNextSignInAction.CONFIRM_SIGN_IN_WITH_NEW_PASSWORD
        AuthSignInStep.RESET_PASSWORD ->
            AuthNextSignInAction.RESET_PASSWORD
        AuthSignInStep.CONFIRM_SIGN_UP ->
            AuthNextSignInAction.CONFIRM_SIGN_UP
    }


