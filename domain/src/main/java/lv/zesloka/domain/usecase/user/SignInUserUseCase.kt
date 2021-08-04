package lv.zesloka.domain.usecase.user

import lv.zesloka.domain.contract.user.UserApi
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.auth.AuthSignIn
import lv.zesloka.domain.model.auth.AuthSignUp
import lv.zesloka.domain.usecase.base.AbstractAsyncResultUseCase

class SignInUserUseCase constructor(
    private val userApi: UserApi
) : AbstractAsyncResultUseCase<SignInUserUseCase.Input, AuthSignIn>() {

    data class Input(val username: String, val password: String)

    override suspend fun act(input: Input): Result<AuthSignIn> {
        val isSuccess = userApi.signIn(input.username, input.password)
        return Result.Success(isSuccess)
    }
}