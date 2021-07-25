package lv.zesloka.data.user

import android.content.Context
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import lv.zesloka.domain.model.User

class AWSUserManager(private val applicationContext: Context) {
    private var mIsInitialized: Boolean = false

    fun isInitialized() = mIsInitialized

    suspend fun init(): Boolean {
        return if (mIsInitialized) {
            mIsInitialized
        } else {
            Amplify.Auth.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            mIsInitialized = true
            mIsInitialized
        }
    }

    suspend fun getUser(): User {
        var user = User.UNKNOWN
        Amplify.Auth.fetchAuthSession(
            {
                user = User(isSignedIn = it.isSignedIn)
            },
            {
                throw it
            }
        )
        return user
    }

}