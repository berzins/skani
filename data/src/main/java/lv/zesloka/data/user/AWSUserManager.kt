package lv.zesloka.data.user

import android.content.Context
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Amplify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import lv.zesloka.domain.model.User
import kotlin.coroutines.resumeWithException


@ExperimentalCoroutinesApi
class AWSUserManager(private val applicationContext: Context) {
    private var mIsInitialized: Boolean = false

    private fun init(): Boolean {
        return if (mIsInitialized) {
            mIsInitialized
        } else {
            Amplify.Auth.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            mIsInitialized = true
            mIsInitialized
        }
    }

    private fun initLazy() {
        if (!mIsInitialized) {
            init()
        }
    }

    suspend fun getUser(): User = suspendCancellableCoroutine { continuation ->
        initLazy()
        Amplify.Auth.fetchAuthSession(
            {
                continuation.resume(User(isSignedIn = it.isSignedIn)) { cancelCause -> /* do nothing */ }
            },
            {
                continuation.resumeWithException(it)
            }
        )
    }

    suspend fun registerUser(username: String, email: String, password: String): AuthSignUpResult =
        suspendCancellableCoroutine { continuation ->
            initLazy()
            val options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .build()
            Amplify.Auth.signUp(username, password, options,
                { result: AuthSignUpResult ->
                    continuation.resume(result) { cancelCause -> /* do nothing */ }
                },
                {
                    continuation.resumeWithException(it)
                }
            )

        }

    suspend fun verifyEmail(username: String, verifyCode: String): AuthSignUpResult =
        suspendCancellableCoroutine { continuation ->
            initLazy()
            Amplify.Auth.confirmSignUp(username, verifyCode,
                { result: AuthSignUpResult ->
                    continuation.resume(result) { cancelCause -> /* blah blah */ }
                },
                {
                    continuation.resumeWithException(it)
                })
        }

    suspend fun signIn(username: String, password: String): AuthSignInResult =
        suspendCancellableCoroutine { continuation ->
            initLazy()
            Amplify.Auth.signIn(username, password,
                { result: AuthSignInResult ->
                    continuation.resume(result) { cancelCause -> /* blah blah */ }
                },
                {
                    continuation.resumeWithException(it)
                })
        }

    suspend fun signOut(): Boolean = suspendCancellableCoroutine { continuation ->
        initLazy()
        Amplify.Auth.signOut(
            {
                continuation.resume(true) { cancelCause -> /* blah blah */ }
            },
            {
                continuation.resumeWithException(it)
            }
        )
    }


}