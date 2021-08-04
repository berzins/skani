package lv.zesloka.domain.model.validators

import lv.zesloka.domain.contract.Validator

// Todo: implement this!!
data class IsValidInputCharacters(val value: String, val invalidChars: String): Validator() {
    override suspend fun getValidationResult(): Decision {
        return AllCool()
    }
}