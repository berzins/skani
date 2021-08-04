package lv.zesloka.skani.presentation.redux.action

sealed class ValidationActions {
    sealed class Auth {
        sealed class Username {
            data class Validate(val username: String)
            data class Success(val username: String)
            data class Failure(val username: String, val reason: ValidationError)
        }

        sealed class Email {
            data class Validate(val email: String)
            data class Success(val email: String)
            data class Failure(val email: String, val reason: ValidationError)
        }

        sealed class Password {
            data class Validate(val password: String)
            data class Success(val password: String)
            data class Failure(val password: String, val reason: ValidationError)
        }
    }
}

enum class ValidationError {
    UNKNOWN
}