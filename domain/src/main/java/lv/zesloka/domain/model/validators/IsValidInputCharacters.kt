package lv.zesloka.domain.model.validators

import lv.zesloka.domain.contract.Validator
import lv.zesloka.domain.model.exception.ValidationInvalidCharactersException

// todo: implement this with regex
data class IsValidInputCharacters(val value: String, val invalidChars: String): Validator() {
    override suspend fun getValidationResult(): Decision {
        val hasInvalidChars = invalidChars.toCharArray().any { value.contains(it) }
        if (!hasInvalidChars) {
            return AllCool()
        } else {
            throw ValidationInvalidCharactersException(this)
        }
    }
}