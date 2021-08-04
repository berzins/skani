package lv.zesloka.skani.presentation.redux.state.navigatoin

import lv.zesloka.skani.presentation.model.navigation.Screen

data class RdxNavigationState(
    val previousScreen: Screen,
    val currentScreen: Screen,
    val isNavigating: Boolean
    ) {
    companion object{}
}