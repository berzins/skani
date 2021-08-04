package lv.zesloka.skani.presentation.redux.state.navigatoin

import lv.zesloka.skani.presentation.model.navigation.Screen
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState

fun RdxNavigationState.Companion.initial() =
    RdxNavigationState(
        currentScreen = Screen.SPLASH,
        previousScreen = Screen.NONE,
        isNavigating = false
    )

fun RdxNavigationState.Companion.selectFrom(state: RdxAppState): RdxNavigationState =
    state.navigationState