package lv.zesloka.domain.usecase.user

import lv.zesloka.domain.contract.user.UserRepo
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.usecase.base.AbstractAsyncResultUseCase
import lv.zesloka.domain.model.User

public class GetUserStateUseCase constructor(
    private val userRepo: UserRepo
):  AbstractAsyncResultUseCase<GetUserStateUseCase.Input, User>() {

    class Input

    override suspend fun act(input: Input): Result<User> {
        val user = userRepo.getUser()
        return Result.Success(user)
    }
}