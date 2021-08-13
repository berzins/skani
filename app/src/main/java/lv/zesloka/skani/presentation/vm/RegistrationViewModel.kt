package lv.zesloka.skani.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lv.zesloka.domain.usecase.base.ErrorCode
import lv.zesloka.skani.di.qualifiyer.RegisterErrorSubscriber
import lv.zesloka.skani.presentation.model.dialog.SingleActionDialog
import lv.zesloka.skani.presentation.model.dialog.genericError
import lv.zesloka.skani.presentation.model.navigation.Screen
import lv.zesloka.skani.presentation.model.text.AppStringId
import lv.zesloka.skani.presentation.model.text.AppStringId.*
import lv.zesloka.skani.presentation.redux.ActionDispatcher
import lv.zesloka.skani.presentation.redux.AppStateProvider
import lv.zesloka.skani.presentation.redux.AppStoreSubscriber
import lv.zesloka.skani.presentation.redux.ErrorSubscriber
import lv.zesloka.skani.presentation.redux.action.NavigationActions
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.action.ValidationActions
import lv.zesloka.skani.presentation.redux.action.ValidationActions.Field
import lv.zesloka.skani.presentation.redux.action.ValidationActions.Target
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.auth.*
import lv.zesloka.skani.presentation.vm.contract.ResolverProvider
import javax.inject.Inject

open class RegistrationViewModel : ViewModel() {

    @Inject
    protected lateinit var dispatcher: ActionDispatcher

    @Inject
    protected lateinit var storeSubscriber: AppStoreSubscriber

    @Inject
    lateinit var stateProvider: AppStateProvider<RdxAppState>

    @Inject
    lateinit var strings: ResolverProvider<String>

    @Inject
    @RegisterErrorSubscriber
    lateinit var registerErrorSubscriber: ErrorSubscriber

    private var stage: MutableLiveData<RdxSignUpStage> = MutableLiveData()

    val usernameError: MutableLiveData<String> = MutableLiveData()
    val emailError: MutableLiveData<String> = MutableLiveData()
    val passwordError: MutableLiveData<String> = MutableLiveData()
    val errorDialog: MutableLiveData<SingleActionDialog> = MutableLiveData()
    val isRegisterEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getAuthStage(): LiveData<RdxSignUpStage> = stage

    fun init() {
        stage.postValue(RdxRegistration.selectFrom(stateProvider.getState()).signUpStage)
        storeSubscriber.onRender { render(it) }
        registerErrorSubscriber.doOnError { onError(it) }
    }

    fun onRegisterAction(username: String, email: String, password: String) {
        dispatcher.dispatch(UserActions.Register.SignUp.Start(username, email, password))
    }

    fun onBack() {
        dispatcher.dispatch(NavigationActions.StartNavigation(Screen.LOGIN))
    }

    private fun render(state: RdxAppState) {
        val authState = RdxAuthState.selectFrom(state)
        val signUpStage = authState.registration.signUpStage
        if (stage.value != signUpStage) {
            stage.postValue(signUpStage)
        }

        val input = RdxRegistration.selectFrom(state).input
        updateInputErrors(input)

        val isRegEnabled = isRegisterActionEnabled(input)
        isRegisterEnabled.postValue(isRegEnabled)

        if (isLoading.value != authState.registration.isInLoadingState) {
            isLoading.postValue(authState.registration.isInLoadingState)
        }
    }

    private fun onError(code: Int) {
        when (code) {
            ErrorCode.INVALID_SIGN_UP_INPUT -> errorDialog.postValue(
                SingleActionDialog.invalidSignUpInputDialog(strings)
            )
            ErrorCode.INVALID_EMAIL_VERIFY_CODE -> errorDialog.postValue(
                SingleActionDialog.invalidEmailVerifyCodeDialog(strings)
            )
            else -> errorDialog.postValue(
                SingleActionDialog.genericError(strings) {}
            )
        }
    }

    fun onVerifyCode(code: String) {
        val regState = RdxRegistration.selectFrom(stateProvider.getState())
        dispatcher.dispatch(
            UserActions.Register.VerifyEmail.Start(
                username = regState.nextStep.user.username,
                code = code
            )
        )
    }

    fun onOnComplete() {
        dispatcher.dispatch(UserActions.Register.Complete())
    }

    fun onUsernameTextChanged(username: String) {
        dispatcher.dispatch(
            ValidationActions.String.Validate(Target.SIGN_UP, Field.USERNAME, username)
        )
    }

    fun onEmailTextChangedListener(email: String) {
        dispatcher.dispatch(
            ValidationActions.String.Validate(Target.SIGN_UP, Field.EMAIL, email)
        )
    }

    fun onPasswordTextChanged(password: String) {
        dispatcher.dispatch(
            ValidationActions.String.Validate(Target.SIGN_UP, Field.PASSWORD, password)
        )
    }


    private fun updateInputErrors(input: RdxRegistrationInput) {
        when (input.username.errorCode) {
            ErrorCode.NONE -> usernameError.postValue("")
            ErrorCode.VALIDATION_STRING_TOO_SHORT ->
                postError(usernameError, ERR_MSG_USERNAME_TOO_SHORT)
            ErrorCode.VALIDATION_STRING_TOO_LONG ->
                postError(usernameError, ERR_MSG_USERNAME_TOO_LONG)
            else -> postError(usernameError, ERR_MSG_UNKNOWN_ERROR)
        }

        when (input.email.errorCode) {
            ErrorCode.NONE ->
                emailError.postValue("")
            ErrorCode.VALIDATION_INVALID_EMAIL ->
                postError(emailError, ERR_MSG_EMAIL_INVALID)
            else -> postError(emailError, ERR_MSG_UNKNOWN_ERROR)
        }

        when (input.password.errorCode) {
            ErrorCode.NONE -> passwordError.postValue("")
            ErrorCode.VALIDATION_EMPTY_STRING ->
                postError(passwordError, ERR_MSG_PASSWORD_EMPTY)
            else -> postError(passwordError, ERR_MSG_UNKNOWN_ERROR)
        }
    }

    private fun postError(error: MutableLiveData<String>, id: AppStringId) {
        val msg = strings.get(id).get()
        error.postValue(msg)
    }

    private fun isRegisterActionEnabled(input: RdxRegistrationInput): Boolean =
        input.email.isValid && input.username.isValid && input.password.isValid
}

fun SingleActionDialog.Companion.invalidSignUpInputDialog(
    strings: ResolverProvider<String>
) = SingleActionDialog(
    title = strings.get(TITLE_SIGN_UP).get(),
    message = strings.get(ERR_MSG_INVALID_SIGN_UP_INPUT).get(),
    actionLabel = strings.get(LABEL_ACTION_POSITIVE).get(),
    action = {}
)

fun SingleActionDialog.Companion.invalidEmailVerifyCodeDialog(
    strings: ResolverProvider<String>
) = SingleActionDialog(
    title = strings.get(TITLE_SIGN_UP).get(),
    message = strings.get(ERR_MSG_INVALID_EMAIL_VERIFY_CODE).get(),
    actionLabel = strings.get(LABEL_ACTION_POSITIVE).get(),
    action = {}
)

