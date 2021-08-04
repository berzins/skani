package lv.zesloka.domain.model.validators

import lv.zesloka.domain.contract.Validator

data class IsStringNotEmpty(val value: String): Validator() {
    override suspend fun getValidationResult(): Decision {
        return if (value.isNotBlank()) AllCool() else NotCool(
            this
        )
    }
}