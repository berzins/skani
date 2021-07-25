package lv.zesloka.domain.usecase.base

import lv.zesloka.domain.model.Result
import java.lang.Exception

abstract class AbstractAsyncResultUseCase<Input, Output> {

    public suspend fun runWith(input: Input): Result<Output> =
        try {
            act(input)
        } catch (e: Exception) {
            Result.Error(ErrorCodes.UNKNOWN, e)
        }


    protected abstract suspend fun act(input: Input): Result<Output>
}