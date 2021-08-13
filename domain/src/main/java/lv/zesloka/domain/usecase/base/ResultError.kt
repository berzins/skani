package lv.zesloka.domain.usecase.base

class ResultError(
    val code: Int = ErrorCode.UNKNOWN,
    val msg: String = "",
    val raw: Throwable? = null) {

}