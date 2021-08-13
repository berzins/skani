package lv.zesloka.domain.model.exception

import lv.zesloka.domain.contract.Validator
import java.lang.Exception

open class ValidationException(val reason: Validator, message: String): Exception(message)

class ValidationStringCantBeEmptyException(reason: Validator):
        ValidationException(reason, "Did you know? String can't be empty!")

class ValidationStringTooShortException(reason: Validator):
        ValidationException(reason, "Sorry but the string is too short!")

class ValidationStringTooLongException(reason: Validator):
        ValidationException(reason, "Sorry but the string is too long!")

class ValidationInvalidCharactersException(reason: Validator):
        ValidationException(reason, "Invalid input characters!")

class ValidationInvalidEmailException(reason: Validator):
        ValidationException(reason, "Invalid input characters!")

