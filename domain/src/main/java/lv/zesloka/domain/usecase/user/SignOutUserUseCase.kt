package lv.zesloka.domain.usecase.user

import lv.zesloka.domain.contract.user.UserApi
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.usecase.base.AbstractAsyncResultUseCase

class SignOutUserUseCase constructor(
    private val userApi: UserApi
) : AbstractAsyncResultUseCase<SignOutUserUseCase.Input, Boolean>() {

    class Input

    override suspend fun act(input: Input): Result<Boolean> {
        val isSuccess = userApi.signOut()
        return Result.Success(isSuccess)
    }
}