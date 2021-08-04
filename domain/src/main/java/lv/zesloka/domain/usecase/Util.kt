package lv.zesloka.domain.usecase

import lv.zesloka.domain.model.Result

fun <T> Result<T>.toErrorString(): String =
    if (this is Result.Error) this.exception.message.toString() else "Not an error."