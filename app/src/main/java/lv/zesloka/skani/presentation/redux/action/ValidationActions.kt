package lv.zesloka.skani.presentation.redux.action

sealed class ValidationActions {
    class UnsupportedRequest(request: String.Validate)

    sealed class String {
        data class Validate(val target: Target, val field: Field, val value: kotlin.String)
        data class Success(val target: Target, val field: Field, val value: kotlin.String)
        data class Error(
            val target: Target,
            val field: Field,
            val value: kotlin.String,
            val errorCode: Int
        )
    }


    /**
     * Such as screenS widgets and any other logical identifiers what can have things to validate
     */
    enum class Target {
        SIGN_UP
    }

    /**
     * A field name to indicate what precisely is validated
     */
    enum class Field {
        USERNAME,
        EMAIL,
        PASSWORD,
        VERIFICATION_CODE
    }
}

//enum class ValidationError {
//    UNKNOWN
//}

