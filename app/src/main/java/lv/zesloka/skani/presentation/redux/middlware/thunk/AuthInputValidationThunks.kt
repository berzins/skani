package lv.zesloka.skani.presentation.redux.middlware.thunk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.usecase.validation.ValidateEmailUseCase
import lv.zesloka.domain.usecase.validation.ValidatePasswordUseCase
import lv.zesloka.domain.usecase.validation.ValidateUsernameUseCase
import lv.zesloka.skani.di.qualifiyer.IOCoroutineContext
import lv.zesloka.skani.presentation.redux.action.ValidationActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.Thunk
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


interface ValueValidationThunk<Input> {
    fun validate(input: Input): Thunk<RdxAppState>
}

abstract class AbstractValueValidationThunk<Input>(private val context: CoroutineContext) :
    ValueValidationThunk<Input>, CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = context + job

    protected abstract suspend fun act(input: Input, dispatch: Dispatcher)

    override fun validate(input: Input): Thunk<RdxAppState> = { dispatch, _, _ ->
        launch {
            act(input, dispatch)
        }
    }
}

class ValidateUsernameThunk @Inject constructor(
    @IOCoroutineContext context: CoroutineContext,
    private val validateUsernameUseCase: ValidateUsernameUseCase
) : AbstractValueValidationThunk<ValidationActions.String.Validate>(context) {

    override suspend fun act(input: ValidationActions.String.Validate, dispatch: Dispatcher) {
        val result = validateUsernameUseCase.runWith(ValidateUsernameUseCase.Input(input.value))
        when (result) {
            is Result.Success -> dispatch(input.toSuccess())
            is Result.Error -> dispatch(input.toError(result.code))
        }
    }
}

class ValidatePasswordThunk @Inject constructor(
    @IOCoroutineContext context: CoroutineContext,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : AbstractValueValidationThunk<ValidationActions.String.Validate>(context) {

    override suspend fun act(input: ValidationActions.String.Validate, dispatch: Dispatcher) {
        val result = validatePasswordUseCase.runWith(ValidatePasswordUseCase.Input(input.value))
        when (result) {
            is Result.Success -> dispatch(input.toSuccess())
            is Result.Error -> dispatch(input.toError(result.code))
        }
    }
}

class ValidateEmailThunk @Inject constructor(
    @IOCoroutineContext context: CoroutineContext,
    private val validateEmailUseCase: ValidateEmailUseCase
) : AbstractValueValidationThunk<ValidationActions.String.Validate>(context) {

    override suspend fun act(input: ValidationActions.String.Validate, dispatch: Dispatcher) {
        val result = validateEmailUseCase.runWith(ValidateEmailUseCase.Input(input.value))
        when (result) {
            is Result.Success ->
                dispatch(input.toSuccess())
            is Result.Error ->
                dispatch(input.toError(result.code))
        }
    }
}


fun ValidationActions.String.Validate.toSuccess(): ValidationActions.String.Success =
    ValidationActions.String.Success(
        target = this.target,
        field = this.field,
        value = this.value
    )

fun ValidationActions.String.Validate.toError(errorCode: Int): ValidationActions.String.Error =
    ValidationActions.String.Error(
        target = this.target,
        field = this.field,
        value = this.value,
        errorCode = errorCode
    )
