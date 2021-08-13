package lv.zesloka.skani

import kotlinx.coroutines.runBlocking
import lv.zesloka.domain.contract.user.UserApi
import lv.zesloka.domain.model.Constants
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.exception.InvalidEmailVerifyCodeException
import lv.zesloka.domain.model.exception.InvalidSignUpInputException
import lv.zesloka.domain.model.exception.InvalidUsernameOrPasswordException
import lv.zesloka.domain.usecase.base.ErrorCode
import lv.zesloka.domain.usecase.user.SignInUserUseCase
import lv.zesloka.domain.usecase.user.SignUpUserUseCase
import lv.zesloka.domain.usecase.user.VerifyEmailUseCase
import lv.zesloka.domain.usecase.validation.ValidateEmailUseCase
import lv.zesloka.domain.usecase.validation.ValidateUsernameUseCase
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.BDDMockito.given

class UseCaseTests {

    @Test
    fun testSignInUseCase_givenAPIThrowsInvalidUsernameOrPasswordException_ReturnErrorCodeInvalidUsernameOrPassword() =
        runBlocking {
            val userApiMock = mock(UserApi::class.java)
            given(userApiMock.signIn("", "")).willAnswer {
                throw InvalidUsernameOrPasswordException(
                    null
                )
            }

            val useCase = SignInUserUseCase(userApiMock)
            val result = useCase.runWith(SignInUserUseCase.Input("", ""))
            assert(result is Result.Error && result.code == ErrorCode.INVALID_CREDENTIALS)
        }


    @Test
    fun testSingUpUsecase_giveAPIThrowsInvalidSignUpInputException_ReturnErrorInvalidSignUpInput() =
        runBlocking {
            val userApiMock = mock(UserApi::class.java)
            given(userApiMock.signUpUser("", "", ""))
                .willAnswer { throw InvalidSignUpInputException(null) }

            val useCase = SignUpUserUseCase(userApiMock)
            val result = useCase.runWith(SignUpUserUseCase.Input("", "", ""))
            assert(result is Result.Error && result.code == ErrorCode.INVALID_SIGN_UP_INPUT)
        }

    @Test
    fun testVerifyEmailUseCase_giveAPIThrowsInvalidEmailVerifyCodeException_ReturnErrorInvalidEmailVerifyCode() =
        runBlocking {
            val userApiMock = mock(UserApi::class.java)
            given(userApiMock.verifyEmail("", ""))
                .willAnswer { throw InvalidEmailVerifyCodeException(null) }

            val useCase = VerifyEmailUseCase(userApiMock)
            val result = useCase.runWith(VerifyEmailUseCase.Input("",""))
            assert(result is Result.Error && result.code == ErrorCode.INVALID_EMAIL_VERIFY_CODE)
        }

    @Test
    fun testValidateEmailUseCase_givenValidEmailAddress_ReturnSuccess() = runBlocking {
        val email = "test@test.test"
        val useCase = ValidateEmailUseCase()
        val result = useCase.runWith(ValidateEmailUseCase.Input(email))
        assert(result is Result.Success)
    }

    @Test
    fun testValidateEmailUseCase_givenEmptyString_ReturnError() = runBlocking {
        val result = ValidateEmailUseCase().runWith(ValidateEmailUseCase.Input(""))
        assert(result is Result.Error)
    }

    @Test
    fun testValidateEmailUseCase_giveBlankString_ReturnError() = runBlocking {
        val result = ValidateEmailUseCase().runWith(ValidateEmailUseCase.Input("   "))
        assert(result is Result.Error)
    }

    @Test
    fun testValidateEmailUseCase_giveEmailWithSpaces_ReturnError() = runBlocking {
        val result = ValidateEmailUseCase().runWith(ValidateEmailUseCase.Input(
            "t est@te st.test"
        ))
        assert(result is Result.Error)
    }

    @Test
    fun testValidateUsernameUseCase_givenStringIsEmpty_ReturnError() = runBlocking {
        val result = ValidateUsernameUseCase().runWith(ValidateUsernameUseCase.Input(""))
        assert(result is Result.Error)
    }

    @Test
    fun testValidateUsernameUseCase_givenStringIsValidLengthWithInvalidChars_ReturnError() = runBlocking {
        val result = ValidateUsernameUseCase().runWith(
            ValidateUsernameUseCase.Input(Constants.ILLEGAL_USERNAME_CHARS)
        )
        assert(result is Result.Error && result.code == ErrorCode.VALIDATION_INVALID_CHARS)
    }

    @Test
    fun testValidateUsernameUseCase_givenStringIsTooShort_ReturnErrorCodeTooShort() =
        runBlocking {
            val result = ValidateUsernameUseCase().runWith(
                ValidateUsernameUseCase.Input("ome")
            )
            assert(result is Result.Error && result.code == ErrorCode.VALIDATION_STRING_TOO_SHORT)
        }

    @Test
    fun testValidateUsernameUseCase_givenStringIsTooLong_ReturnErrorCodeTooLong() =
        runBlocking {
            val result = ValidateUsernameUseCase().runWith(
                ValidateUsernameUseCase.Input(String.ofLength('o', 21))
            )
            assert(result is Result.Error && result.code == ErrorCode.VALIDATION_STRING_TOO_LONG)
        }

    @Test
    fun testValidateUsernameUses_givenStringIsEmpty_ReturnErrorCodeEmptyString() =
        runBlocking {
            val result = ValidateUsernameUseCase().runWith(
                ValidateUsernameUseCase.Input("")
            )
            assert(result is Result.Error && result.code == ErrorCode.VALIDATION_EMPTY_STRING)
        }

}

fun String.Companion.ofLength(char: Char, length: Int): String {
    var str = ""
    (0 until length).forEach { _ -> str += char }
    return str
}