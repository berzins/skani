package lv.zesloka.skani

import kotlinx.coroutines.runBlocking
import lv.zesloka.domain.model.exception.ValidationStringCantBeEmptyException
import lv.zesloka.domain.model.exception.ValidationStringTooLongException
import lv.zesloka.domain.model.exception.ValidationStringTooShortException
import lv.zesloka.domain.model.validators.AllCool
import lv.zesloka.domain.model.validators.IsStringLengthEqualOrLess
import lv.zesloka.domain.model.validators.IsStringLengthEqualOrMore
import lv.zesloka.domain.model.validators.IsStringNotEmpty
import org.junit.Assert
import org.junit.Test

class ValidatorUnitTests {

    class IsNotEmptyStringValidatorTests {

        @Test
        fun givenNotEmptyString_ReturnAllCool() = runBlocking {
            val validator = IsStringNotEmpty("not an empty string")
            val result = validator.validate()
            assert(result is AllCool)
        }

        @Test
        fun givenBlankString_ThrowValidationStringCantBeEmptyException() = runBlocking {
            val validator = IsStringNotEmpty("   ")
            try {
                val result = validator.validate()
                Assert.fail(
                    "ValidationStringCantBeEmptyException was " +
                            "expected for a blank string"
                )
            } catch (e: ValidationStringCantBeEmptyException) {
                // success
            }
        }

        @Test
        fun givenEmptyString_ThrowValidationStringCantBeEmptyException() = runBlocking {
            val validator = IsStringNotEmpty("")
            try {
                val result = validator.validate()
                Assert.fail(
                    "ValidationStringCantBeEmptyException was " +
                            "expected for an empty string"
                )
            } catch (e: ValidationStringCantBeEmptyException) {
                // success
            }
        }

    }

    class IsStringLengthEqualOrMoreValidatorTests {

        @Test
        fun givenIsShorterThanRequired_throwValidationStringTooShortException() = runBlocking {
            val str = "123"
            val minLength = 4
            val validator = IsStringLengthEqualOrMore(str, minLength)
            try {
                val result = validator.validate()
                Assert.fail(
                    "ValidationStringTooShortException was expected for " +
                            "string: $str, minLength: $minLength"
                )
            } catch (e: ValidationStringTooShortException) {
                // success
            }
        }

        @Test
        fun givenStringIsEqualMinLength_returnAllCool() = runBlocking {
            val str = "1234"
            val minLength = 4
            val validator = IsStringLengthEqualOrMore(str, minLength)
            val result = validator.validate()
            assert(result is AllCool)
        }

        @Test
        fun givenStringIsMoreThanMinLength_returnAllCool() = runBlocking {
            val validator = IsStringLengthEqualOrMore("12345", 4)
            val result = validator.validate()
            assert(result is AllCool)
        }
    }

    class IsStringLengthEqualOrLessValidatorTests {
        @Test
        fun givenStringIsLongerThanMaxLength_throwValidationStringTooLongException() =
            runBlocking {
                try {
                    val str = "12345"
                    val maxLength = 4
                    val result = IsStringLengthEqualOrLess(str, maxLength).validate()
                    Assert.fail(
                        "ValidationStringTooLongException was expected for " +
                                "string: $str, maxLength: $maxLength"
                    )
                } catch (e: ValidationStringTooLongException) {
                    //success
                }
            }

        @Test
        fun givenStringIsEqualWithMaxLength_returnAllCool() = runBlocking {
            val result = IsStringLengthEqualOrLess("1234", 4).validate()
            assert(result is AllCool)
        }

        @Test
        fun givenStringIsShorterThanMaxLength_returnAllCool() = runBlocking {
            val result = IsStringLengthEqualOrLess("123", 4).validate()
            assert(result is AllCool)
        }
    }

    // The goal is to test if all chained validators are called
    // and throws exceptions when necessary
    class CompoundValidatorChainUnitTests {
        @Test
        fun giveStringIsInMinMaxRange_returnAllCool() = runBlocking {
            val str = "1234"
            val result = IsStringLengthEqualOrMore(str, 3)
                .and(IsStringLengthEqualOrLess(str, 5))
                .validate()
            assert(result is AllCool)
        }

        @Test
        fun givenStringIsLongerThanMinMaxRange_throwValidationStringTooLongException() =
            runBlocking {
                try {
                    val str = "123456"
                    val minLength = 3
                    val maxLength = 5
                    IsStringLengthEqualOrMore(str, minLength)
                        .and(IsStringLengthEqualOrLess(str, maxLength))
                        .validate()
                    Assert.fail(
                        "ValidationStringTooLongException was expected for " +
                                "string: $str, minLength: $minLength, maxLength: $maxLength"
                    )

                } catch (e: ValidationStringTooLongException) {
                    //success
                }

            }

        @Test
        fun givenStringIsShorterThanMinMaxRange_throwValidationStringTooLongException() =
            runBlocking {
                try {
                    val str = "123"
                    val minLength = 4
                    val maxLength = 7
                    IsStringLengthEqualOrMore(str, minLength)
                        .and(IsStringLengthEqualOrLess(str, maxLength))
                        .validate()
                    Assert.fail(
                        "ValidationStringTooShortException was expected for " +
                                "string: $str, minLength: $minLength, maxLength: $maxLength"
                    )

                } catch (e: ValidationStringTooShortException) {
                    //success
                }

            }
    }


}