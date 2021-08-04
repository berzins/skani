package lv.zesloka.domain.usecase.user

import lv.zesloka.domain.contract.user.UserApi
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.auth.AuthSignUp
import lv.zesloka.domain.usecase.base.AbstractAsyncResultUseCase

class VerifyEmailUseCase constructor(
    private val userApi: UserApi
) : AbstractAsyncResultUseCase<VerifyEmailUseCase.Input, AuthSignUp>() {

    data class Input(val username: String, val verifyCode: String)

    override suspend fun act(input: Input): Result<AuthSignUp> {
        val isSuccess: AuthSignUp = userApi.verifyEmail(input.username, input.verifyCode)
        return Result.Success(isSuccess)
    }
}