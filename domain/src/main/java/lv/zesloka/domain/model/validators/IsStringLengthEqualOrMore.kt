package lv.zesloka.domain.model.validators

import lv.zesloka.domain.contract.Validator
import lv.zesloka.domain.model.exception.ValidationStringTooShortException

data class IsStringLengthEqualOrMore(val value: String, val minLength: Int): Validator() {
    override suspend fun getValidationResult(): Decision {
        return if (value.length >= minLength) AllCool()
        else throw ValidationStringTooShortException(this)
    }
}