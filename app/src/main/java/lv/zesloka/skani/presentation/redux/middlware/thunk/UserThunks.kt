package lv.zesloka.skani.presentation.redux.middlware.thunk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.auth.signin.AuthNextSignInAction
import lv.zesloka.domain.model.auth.signup.NextSignUpAction
import lv.zesloka.domain.model.validators.AllCool
import lv.zesloka.domain.model.validators.Decision
import lv.zesloka.domain.usecase.base.ErrorCode
import lv.zesloka.domain.usecase.toErrorString
import lv.zesloka.domain.usecase.user.*
import lv.zesloka.domain.usecase.validation.ValidateEmailUseCase
import lv.zesloka.domain.usecase.validation.ValidatePasswordUseCase
import lv.zesloka.domain.usecase.validation.ValidateUsernameUseCase
import lv.zesloka.skani.di.qualifiyer.IOCoroutineContext
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.Thunk
import timber.log.Timber
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
                dispatch(UserActions.UserStateError(ErrorCode.UNKNOWN, error.exception.toString()))
            }
        }
    }

    fun registerUser(username: String, email: String, password: String): Thunk<RdxAppState> =
        { dispatch, _, _ ->
            launch {
                val isInputValid = validateSignUpInput(username, email, password)
                if (isInputValid) {

                    val result = signUpUserUseCase.runWith(
                        SignUpUserUseCase.Input(username, email, password)
                    )

                    when (result) {
                        is Result.Success -> {
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
                        }
                        is Result.Error -> dispatch(
                            UserActions.Register.SignUp.Error(result.code)
                        )
                        else -> Timber.e("Something went terribly wrong!")
                    }

                    if (result is Result.Success && result.data.isSignUpComplete) {

                    } else {

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
            when (result) {
                is Result.Success ->
                    dispatch(UserActions.Register.VerifyEmail.Success(result.data))
                is Result.Error ->
                    dispatch(UserActions.Register.VerifyEmail.Error(result.code))
            }
        }
    }

    fun signIn(username: String, password: String): Thunk<RdxAppState> = { dispatch, _, _ ->
        launch {
            val result = signInUserUseCase.runWith(
                SignInUserUseCase.Input(
                    username = username,
                    password = password
                )
            )
            when (result) {
                is Result.Success -> {
                    with(result.data) {
                        if (this.isSignInComplete
                            && this.nextStep.nextSignInAction == AuthNextSignInAction.DONE
                        ) {
                            dispatch(UserActions.SignIn.Success(this))
                        } else {
                            //todo: handle next steps here.
                        }
                    }
                }
                is Result.Error -> {
                    dispatch(UserActions.SignIn.Error(result.code))
                }
            }

        }
    }

    fun signOut(): Thunk<RdxAppState> = { dispatch, _, _ ->
        launch {
            val result = signOutUserUseCase.runWith(SignOutUserUseCase.Input())
            if (result is Result.Success) {
                dispatch(UserActions.SignOut.Success())
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


