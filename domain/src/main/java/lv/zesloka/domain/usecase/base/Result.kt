package lv.zesloka.domain.usecase.base

//class Result<T> (val data: T? = null, val error: ResultError? = null) {
//
//    companion object {
//        fun <T> success(data: T) = Result(data = data)
//
//        fun <T> failure(code: Int) = ResultError(code = code)
//
//        fun <T> failure(code: Int, msg: String) = ResultError(code = code, msg = msg)
//
//        fun <T> failure(code: Int, msg: String, raw: Throwable) =
//            ResultError( code = code, msg = msg, raw = raw)
//    }
//
//    public fun isSuccessful() = data != null && error == null
//}