package lv.zesloka.data.user

import com.amplifyframework.auth.AuthCodeDeliveryDetails
import com.amplifyframework.auth.AuthUser
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.auth.result.step.AuthSignUpStep
import kotlinx.coroutines.ExperimentalCoroutinesApi
import lv.zesloka.domain.model.User
import lv.zesloka.domain.model.auth.*

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
        val result = mUserManager.registerUser(username, email, password)
        return AuthSignUp.from(result)
    }

    override suspend fun verifyEmail(username: String, verifyCode: String): lv.zesloka.domain.model.auth.AuthSignUp {
        val result =  mUserManager.verifyEmail(username, verifyCode)
        return AuthSignUp.from(result)
    }

    override suspend fun signIn(username: String, password: String): AuthSignIn {
        val result = mUserManager.signIn(username, password)
        return AuthSignIn.from(result)
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
        deliveryType = this.codeDeliveryDetails?.toAuthCodeDeliveryType() ?: AuthActoinDeliveryType.NONE
    )


fun AuthSignUpStep.toAuthSignUpStepAction(): NextSignUpAction =
    when (this) {
        AuthSignUpStep.CONFIRM_SIGN_UP_STEP -> NextSignUpAction.CONFIRM_SIGN_UP_STEP
        AuthSignUpStep.DONE -> NextSignUpAction.DONE
    }

fun AuthCodeDeliveryDetails.toAuthCodeDeliveryType(): AuthActoinDeliveryType =
    when(this.deliveryMedium) {
        AuthCodeDeliveryDetails.DeliveryMedium.EMAIL -> AuthActoinDeliveryType.EMAIL
        AuthCodeDeliveryDetails.DeliveryMedium.PHONE -> AuthActoinDeliveryType.PHONE
        AuthCodeDeliveryDetails.DeliveryMedium.SMS -> AuthActoinDeliveryType.SMS
        AuthCodeDeliveryDetails.DeliveryMedium.UNKNOWN -> AuthActoinDeliveryType.UNKNOWN
    }

fun AuthSignIn.Companion.from(result: AuthSignInResult): AuthSignIn =
    AuthSignIn()