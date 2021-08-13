package lv.zesloka.domain.model.exception

import java.lang.Exception

class InvalidSignUpInputException(cause: Exception?): Exception("Some of auth inputs are invalid.",cause) {
}