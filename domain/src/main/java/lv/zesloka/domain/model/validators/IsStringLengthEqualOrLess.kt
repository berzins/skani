package lv.zesloka.domain.model.validators

import lv.zesloka.domain.contract.Validator
import lv.zesloka.domain.model.exception.ValidationStringTooLongException

data class IsStringLengthEqualOrLess(val value: String, val count: Int): Validator() {
    override suspend fun getValidationResult(): Decision {
        return if (value.length <= count)
            AllCool()
        else
            throw ValidationStringTooLongException(this)
    }
}