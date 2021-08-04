package lv.zesloka.skani.presentation.redux.action

import lv.zesloka.skani.presentation.model.navigation.Screen
import lv.zesloka.skani.presentation.model.navigation.ScreenNavigator

sealed class NavigationActions {
    data class StartNavigation(val screen: Screen)
    data class NavigationResult(val screen: Screen, val isSuccess: Boolean)
    data class UnsupportedNavigationRequested(val currentScreen: Screen, val nextScreen: Screen)
}