package lv.zesloka.data.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lv.zesloka.domain.contract.user.UserApi
import lv.zesloka.domain.model.User
import lv.zesloka.domain.model.auth.AuthSignIn
import lv.zesloka.domain.model.auth.AuthSignUp

class UserApiImpl constructor(private val awsDataSource: UserDataSource) : UserApi {
    override suspend fun getUser(): User {
        return withContext(Dispatchers.IO) {
            awsDataSource.getUser()
        }
    }

    override suspend fun signUpUser(username: String, email: String, password: String): AuthSignUp {
        return withContext(Dispatchers.IO) {
            val result = awsDataSource.signUpUser(username, email, password)
            return@withContext result
        }
    }

    override suspend fun verifyEmail(username: String, verifyCode: String): AuthSignUp {
        return awsDataSource.verifyEmail(username, verifyCode)
    }

    override suspend fun signIn(username: String, password: String): AuthSignIn {
        return awsDataSource.signIn(username, password)
    }

    override suspend fun signOut(): Boolean {
        return awsDataSource.signOut()
    }


}

