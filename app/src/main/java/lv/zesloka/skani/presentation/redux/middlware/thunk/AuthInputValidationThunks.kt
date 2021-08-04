package lv.zesloka.skani.presentation.redux.middlware.thunk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.model.validators.AllCool
import lv.zesloka.domain.model.validators.Decision
import lv.zesloka.domain.model.validators.NotCool
import lv.zesloka.domain.usecase.validation.ValidateEmailUseCase
import lv.zesloka.domain.usecase.validation.ValidatePasswordUseCase
import lv.zesloka.domain.usecase.validation.ValidateUsernameUseCase
import lv.zesloka.skani.di.qualifiyer.IOCoroutineContext
import lv.zesloka.skani.presentation.redux.action.ValidationActions
import lv.zesloka.skani.presentation.redux.action.ValidationError
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.Thunk
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AuthInputValidationThunks @Inject constructor(
    @IOCoroutineContext private val context: CoroutineContext,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = context + job

    fun validateUsername(username: String): Thunk<RdxAppState> = { dispatch, _, _ ->
        launch {
            val result = validateUsernameUseCase.runWith(ValidateUsernameUseCase.Input(username))
            processResult(
                result = result,
                value = username,
                onSuccess = { dispatch(ValidationActions.Auth.Username.Success(username)) },
                onFailure = { error ->
                    dispatch(
                        ValidationActions.Auth.Username.Failure(
                            username,
                            error.toAuthValidationError()
                        )
                    )
                }
            )
        }
    }

    fun validatePassword(password: String): Thunk<RdxAppState> = { dispatch, _, _ ->
        launch {
            val result = validatePasswordUseCase.runWith(ValidatePasswordUseCase.Input(password))
            processResult(
                result = result,
                value = password,
                onSuccess = { dispatch(ValidationActions.Auth.Password.Success(password)) },
                onFailure = { error ->
                    dispatch(
                        ValidationActions.Auth.Password.Failure(
                            password,
                            error.toAuthValidationError()
                        )
                    )
                }
            )
        }
    }

    fun validateEmail(email: String): Thunk<RdxAppState> = { dispatch, _, _ ->
        launch {
            val result = validateEmailUseCase.runWith(ValidateEmailUseCase.Input(email))
            processResult(
                result = result,
                value = email,
                onSuccess = { dispatch(ValidationActions.Auth.Password.Success(email)) },
                onFailure = { error ->
                    dispatch(
                        ValidationActions.Auth.Password.Failure(
                            email,
                            error.toAuthValidationError()
                        )
                    )
                }
            )
        }
    }

    private fun processResult(
        result: Result<Decision>,
        value: String,
        onSuccess: () -> Unit,
        onFailure: (err: NotCool) -> Unit
    ) =
        when (result) {
            is Result.Success -> {
                when (val data = result.data) {
                    is AllCool -> onSuccess()
                    is NotCool -> onFailure(data)
                    else -> Timber.e(errStrUnknown(value))
                }
            }
            is Result.Error -> Timber.e(errStringResultError(value, result))
        }


    private fun errStrUnknown(value: String) =
        "Failed to process value:'$value' validation result. Result is unknown."

    private fun errStringResultError(value: String, error: Result.Error) =
        "Error while validating value:'$value'. Code:${error.code}, message: ${error.exception.message}"


}

/**
 * Map Domain specific validators to validation errors
 */
fun NotCool.toAuthValidationError(): ValidationError {
    when (this.problem) {
        // todo: check for failed validators here and return error what fits
    }
    return ValidationError.UNKNOWN

}