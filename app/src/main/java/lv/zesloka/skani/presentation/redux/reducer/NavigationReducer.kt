package lv.zesloka.skani.presentation.redux.reducer

import lv.zesloka.skani.presentation.redux.action.NavigationActions
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.navigatoin.RdxNavigationState
import lv.zesloka.skani.presentation.redux.state.navigatoin.selectFrom

fun navigationReducer(state: RdxAppState, action: Any): RdxNavigationState {

    val navState = RdxNavigationState.selectFrom(state)
    when(action) {
        is NavigationActions.StartNavigation -> {
            return navState.copy(isNavigating = true)
        }
        is NavigationActions.NavigationResult -> {
            return navState.copy(
                isNavigating = false,
                previousScreen = if (action.isSuccess) navState.currentScreen else navState.previousScreen,
                currentScreen =  if (action.isSuccess) action.screen else navState.currentScreen
            )
        }
    }

    return navState
}