package lv.zesloka.domain.usecase.base

class ResultError(
    val code: Int = ErrorCodes.UNKNOWN,
    val msg: String = "",
    val raw: Throwable? = null) {

}