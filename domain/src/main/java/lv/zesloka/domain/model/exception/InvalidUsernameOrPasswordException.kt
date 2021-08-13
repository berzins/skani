package lv.zesloka.domain.model.exception

import java.lang.Exception

class InvalidUsernameOrPasswordException(cause: Exception?):
    Exception("Wrong username or password.", cause) {
}