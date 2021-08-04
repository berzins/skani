package lv.zesloka.skani.presentation.redux.middlware.thunk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.auth.NextSignUpAction
import lv.zesloka.domain.model.validators.AllCool
import lv.zesloka.domain.model.validators.Decision
import lv.zesloka.domain.usecase.base.ErrorCodes
import lv.zesloka.domain.usecase.toErrorString
import lv.zesloka.domain.usecase.user.*
import lv.zesloka.domain.usecase.validation.ValidateEmailUseCase
import lv.zesloka.domain.usecase.validation.ValidatePasswordUseCase
import lv.zesloka.domain.usecase.validation.ValidateUsernameUseCase
import lv.zesloka.skani.di.qualifiyer.IOCoroutineContext
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.action.ValidationActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.Thunk
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserThunks @Inject constructor(
    @IOCoroutineContext private val userCallContext: CoroutineContext,
    private val getUserStateUseCase: GetUserStateUseCase,
    private val signUpUserUseCase: SignUpUserUseCase,
    private val signInUserUseCase: SignInUserUseCase,
    private val signOutUserUseCase: SignOutUserUseCase,
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = userCallContext + job


    fun fetchUserStatus(): Thunk<RdxAppState> = { dispatch, _, _ ->
        dispatch(UserActions.UserStateFetchStart())
        launch {
            val result = getUserStateUseCase.runWith(GetUserStateUseCase.Input())
            if (result is Result.Success) {
                dispatch(UserActions.UserStateSuccess(result.data.isSignedIn))
            } else {
                val error = result as Result.Error
                dispatch(UserActions.UserStateError(ErrorCodes.UNKNOWN, error.exception.toString()))
            }
        }
    }

    fun registerUser(username: String, email: String, password: String): Thunk<RdxAppState> =
        { dispatch, _, _ ->
            launch {
                val isInputValid = validateSignUpInput(username, email, password)
                if (isInputValid) {
                    dispatch(ValidationActions.Auth.Username.Success(username))
                    dispatch(ValidationActions.Auth.Email.Success(email))
                    dispatch(ValidationActions.Auth.Password.Success(password))

                    val result = signUpUserUseCase.runWith(
                        SignUpUserUseCase.Input(username, email, password)
                    )
                    if (result is Result.Success && result.data.isSignUpComplete) {
                        val signUpResult = result.data
                        when (signUpResult.nextSignUpStep.action) {
                            NextSignUpAction.DONE ->
                                dispatch(UserActions.Register.Complete())
                            else -> dispatch(
                                UserActions.Register.SignUp.Success(
                                    signUp = signUpResult
                                )
                            )
                        }
                    } else {
                        dispatch(UserActions.Register.SignUp.Error(result.toErrorString()))
                    }
                }
            }
        }

    fun verifyEmail(username: String, code: String): Thunk<RdxAppState> = { dispatch, _, _ ->
        launch {
            val result = verifyEmailUseCase.runWith(
                VerifyEmailUseCase.Input(
                    username = username,
                    verifyCode = code
                )
            )
            if (result is Result.Success) {
                dispatch(UserActions.Register.VerifyEmail.Success(result.data))
            } else {
                dispatch(
                    UserActions.Register.VerifyEmail.Error(result.toErrorString())
                )
            }
        }
    }

    fun signIn(username: String, password: String): Thunk<RdxAppState> = { dispatch, _, _ ->
        launch {
            val result = signInUserUseCase.runWith(
                SignInUserUseCase.Input(
                username = username,
                password = password
            ))
            if (result is Result.Success) {
                // todo: do magic here
            } else {
                dispatch(UserActions.SignIn.Error(result.toErrorString()))
            }
        }
    }

    fun signOut(): Thunk<RdxAppState> = { dispatch, _, _ ->
        launch {
            val result = signOutUserUseCase.runWith(SignOutUserUseCase.Input())
            if (result is Result.Success) {
                // todo: Navigate to Login
            } else {
                dispatch(UserActions.SignOut.Error(result.toErrorString()))
            }

        }
    }

    private suspend fun validateSignUpInput(
        username: String,
        email: String,
        password: String
    ): Boolean {
        val usernameValidResult = validateUsernameUseCase
            .runWith(ValidateUsernameUseCase.Input(username))
        val emailValidResult = validateEmailUseCase.runWith(ValidateEmailUseCase.Input(email))
        val passwordValidResult = validatePasswordUseCase
            .runWith(ValidatePasswordUseCase.Input(password))
        return isAllInputValid(
            listOf(
                usernameValidResult,
                emailValidResult,
                passwordValidResult
            )
        )
    }

    private fun isResultSuccess(result: Result<Decision>): Boolean =
        result is Result.Success && result.data is AllCool

    private fun isAllInputValid(results: List<Result<Decision>>) =
        results.all { result -> isResultSuccess(result) }
}


