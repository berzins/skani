package lv.zesloka.domain.usecase.validation

import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.validators.*
import lv.zesloka.domain.usecase.base.AbstractAsyncResultUseCase

class ValidateUsernameUseCase: AbstractAsyncResultUseCase<ValidateUsernameUseCase.Input, Decision>() {

    companion object {
        private const val MAX_LENGTH: Int = 20
        private const val MIN_LENGTH: Int = 5
        private const val INVALID_CHARS: String = ""
    }

    data class Input(val username: String)

    override suspend fun act(input: Input): Result<Decision> {
        val decision = IsStringNotEmpty(input.username)
            .and(
                IsStringLengthEqualOrLess(
                    input.username,
                    MAX_LENGTH
                )
            )
            .and(
                IsStringLengthEqualOrMore(
                    input.username,
                    MIN_LENGTH
                )
            )
            .and(
                IsValidInputCharacters(
                    input.username,
                    INVALID_CHARS
                )
            )
            .validate()
        return Result.Success(decision)
    }

}