package lv.zesloka.domain.usecase.base

class ErrorCode {
    companion object {



        /* AUTH */
        const val INVALID_CREDENTIALS = 1000
        const val INVALID_SIGN_UP_INPUT = 1001
        const val INVALID_EMAIL_VERIFY_CODE = 1002

        /* VALIDATION */
        const val VALIDATION_EMPTY_STRING = 2000
        const val VALIDATION_STRING_TOO_SHORT = 2001
        const val VALIDATION_STRING_TOO_LONG = 2002
        const val VALIDATION_INVALID_EMAIL = 2003
        const val VALIDATION_INVALID_CHARS = 2004

        const val NONE = 0
        const val UNKNOWN = -1
    }
}