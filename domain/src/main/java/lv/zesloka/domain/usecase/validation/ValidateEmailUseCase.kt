package lv.zesloka.domain.usecase.validation

import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.validators.Decision
import lv.zesloka.domain.model.validators.IsValidEmailAddress
import lv.zesloka.domain.usecase.base.AbstractAsyncResultUseCase

class ValidateEmailUseCase: AbstractAsyncResultUseCase<ValidateEmailUseCase.Input, Decision>() {

    data class Input(val email: String)

    override suspend fun act(input: Input): Result<Decision> {
        val decision = IsValidEmailAddress(
            input.email
        ).validate()
        return Result.Success(decision)
    }

}