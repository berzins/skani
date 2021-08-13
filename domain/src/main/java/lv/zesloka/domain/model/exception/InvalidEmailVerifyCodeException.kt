package lv.zesloka.domain.model.exception

import java.lang.Exception

class InvalidEmailVerifyCodeException(cause: Exception?) :
    Exception("Email verify code doesn't match", cause)