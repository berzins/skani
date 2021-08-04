package lv.zesloka.domain.model.validators

import lv.zesloka.domain.contract.Validator
import java.util.regex.Pattern

data class IsValidEmailAddress(val email: String): Validator() {

    companion object {
        private val EMAIL_ADDRESS_PATTERN: Pattern =
            Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
            )
    }

    override suspend fun getValidationResult(): Decision {
        val isValidEmail = EMAIL_ADDRESS_PATTERN.matcher(email).matches()
        return if (isValidEmail) AllCool() else NotCool(
            this
        )
    }
}