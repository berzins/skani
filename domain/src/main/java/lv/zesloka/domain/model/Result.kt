package lv.zesloka.domain.model

import java.lang.Exception

sealed class Result<out R> {
    data class Success<T>(val data: T): Result<T>()
    data class Error(val code: Int, val exception: Exception): Result<Nothing>()
}