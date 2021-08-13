package lv.zesloka.domain.usecase.base

import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.exception.*
import java.lang.Exception

abstract class AbstractAsyncResultUseCase<Input, Output> {

    suspend fun runWith(input: Input): Result<Output> =
        try {
            act(input)
        } catch (e: Exception) {
            Result.Error(getErrorCode(e), e)
        }

    protected abstract suspend fun act(input: Input): Result<Output>

    private fun getErrorCode(e: Exception) =
        when(e) {
            /* AUTH */
            is InvalidUsernameOrPasswordException -> ErrorCode.INVALID_CREDENTIALS
            is InvalidSignUpInputException -> ErrorCode.INVALID_SIGN_UP_INPUT
            is InvalidEmailVerifyCodeException -> ErrorCode.INVALID_EMAIL_VERIFY_CODE

            /* VALIDATION */
            is ValidationStringCantBeEmptyException -> ErrorCode.VALIDATION_EMPTY_STRING
            is ValidationStringTooShortException -> ErrorCode.VALIDATION_STRING_TOO_SHORT
            is ValidationStringTooLongException -> ErrorCode.VALIDATION_STRING_TOO_LONG
            is ValidationInvalidEmailException -> ErrorCode.VALIDATION_INVALID_EMAIL
            is ValidationInvalidCharactersException -> ErrorCode.VALIDATION_INVALID_CHARS

            else -> ErrorCode.UNKNOWN
        }

}