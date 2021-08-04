package lv.zesloka.skani.presentation.redux.middlware.thunk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.usecase.base.ErrorCodes
import lv.zesloka.domain.usecase.user.GetUserStateUseCase
import lv.zesloka.skani.di.qualifiyer.IOCoroutineContext
import lv.zesloka.skani.presentation.model.navigation.Screen
import lv.zesloka.skani.presentation.redux.action.NavigationActions
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.Thunk
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AppInitThunks @Inject constructor(
    @IOCoroutineContext private val navigationContext: CoroutineContext,
    private val getUserStateUseCase: GetUserStateUseCase
): CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = navigationContext + job

    fun initOnStart(): Thunk<RdxAppState> = { dispatch, _, _ ->
        launch {
            dispatch(NavigationActions.NavigationResult(Screen.SPLASH, true))
            val result = getUserStateUseCase.runWith(GetUserStateUseCase.Input())
            if (result is Result.Success) {
                dispatch(UserActions.UserStateSuccess(result.data.isSignedIn))
                if (result.data.isSignedIn) {
                    dispatch(NavigationActions.StartNavigation(Screen.HOME))
                } else {
                    dispatch(NavigationActions.StartNavigation(Screen.LOGIN))
                }
            } else {
                val error = result as Result.Error
                dispatch(UserActions.UserStateError(ErrorCodes.UNKNOWN, error.exception.toString()))
            }
        }
    }
}