package lv.zesloka.domain.model.validators

import lv.zesloka.domain.contract.Validator
import lv.zesloka.domain.model.exception.ValidationStringCantBeEmptyException

data class IsStringNotEmpty(val value: String): Validator() {
    override suspend fun getValidationResult(): Decision {
        return if (value.isNotBlank()) AllCool() else
            throw ValidationStringCantBeEmptyException(this)
    }
}