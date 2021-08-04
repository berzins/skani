package lv.zesloka.domain.model.validators

import lv.zesloka.domain.contract.Validator

data class IsStringLengthEqualOrLess(val value: String, val count: Int): Validator() {
    override suspend fun getValidationResult(): Decision {
        return if (value.length <= count) AllCool() else NotCool(
            this
        )
    }
}