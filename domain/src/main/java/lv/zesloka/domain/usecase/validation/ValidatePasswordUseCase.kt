package lv.zesloka.domain.usecase.validation

import lv.zesloka.domain.model.validators.Decision
import lv.zesloka.domain.model.validators.IsValidEmailAddress
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.validators.IsStringNotEmpty
import lv.zesloka.domain.usecase.base.AbstractAsyncResultUseCase

class ValidatePasswordUseCase: AbstractAsyncResultUseCase<ValidatePasswordUseCase.Input, Decision>() {

    data class Input(val password: String)

    override suspend fun act(input: Input): Result<Decision> {
        val decision = IsStringNotEmpty(
            input.password
        ).validate()
        return Result.Success(decision)
    }

}