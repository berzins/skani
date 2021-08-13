package lv.zesloka.skani.presentation.redux.middlware.thunk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lv.zesloka.skani.di.qualifiyer.UICoroutineContext
import lv.zesloka.skani.presentation.model.navigation.ApplicationNavigation
import lv.zesloka.skani.presentation.model.navigation.Screen
import lv.zesloka.skani.presentation.redux.action.NavigationActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.navigatoin.RdxNavigationState
import lv.zesloka.skani.presentation.redux.state.navigatoin.selectFrom
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.Thunk
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NavigationThunks @Inject constructor(
    @UICoroutineContext private val navigationContext: CoroutineContext,
    private val navigation: ApplicationNavigation
) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = navigationContext + job

    fun navigateWith(
        navAction: NavigationActions.StartNavigation
    ): Thunk<RdxAppState> = { dispatch, getState, _ ->
        launch {
            val navState = RdxNavigationState.selectFrom(getState())
            val currentScreen = navState.currentScreen
            val navigator = navigation.getNavigator(currentScreen)
            if (navigator != null) {
                val isSuccess = navigator.navigateTo(navAction.screen)
                dispatch(NavigationActions.NavigationResult(navAction.screen, isSuccess))
                if (!isSuccess) {
                    dispatchNotSupported(dispatch, currentScreen, navAction.screen)
                }
            } else {
                // Just to be seen in logs.
                dispatchNotSupported(dispatch, currentScreen, navAction.screen)
            }
        }
    }

    private fun dispatchNotSupported(dispatch: Dispatcher, current: Screen, next: Screen) =
        dispatch(NavigationActions.UnsupportedNavigationRequested(current, next))


}