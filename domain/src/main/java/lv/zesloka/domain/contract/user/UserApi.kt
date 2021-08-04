package lv.zesloka.domain.contract.user

import lv.zesloka.domain.model.User
import lv.zesloka.domain.model.auth.AuthSignIn
import lv.zesloka.domain.model.auth.AuthSignUp

public interface UserApi {
    suspend fun getUser(): User

    suspend fun signUpUser(username: String, email: String, password: String): AuthSignUp

    suspend fun verifyEmail(username: String, verifyCode1: String): AuthSignUp

    suspend fun signIn(username: String, password: String): AuthSignIn

    suspend fun signOut(): Boolean
}