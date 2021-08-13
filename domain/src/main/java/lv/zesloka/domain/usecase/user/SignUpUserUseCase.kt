package lv.zesloka.domain.usecase.user

import lv.zesloka.domain.contract.user.UserApi
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.auth.signup.AuthSignUp
import lv.zesloka.domain.usecase.base.AbstractAsyncResultUseCase

class SignUpUserUseCase constructor(
    private val userApi: UserApi
) : AbstractAsyncResultUseCase<SignUpUserUseCase.Input, AuthSignUp>() {

    data class Input(val username: String, val email: String, val password: String)

    override suspend fun act(input: Input): Result<AuthSignUp> {
        val isSuccess = userApi.signUpUser(input.username, input.email, input.password)
        return Result.Success(isSuccess)
    }
}