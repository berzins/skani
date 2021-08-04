package lv.zesloka.skani.presentation.model.navigation

interface ScreenNavigator {
    /**
     * Navigate to desired screen. Return true if success.
     */
    fun navigateTo(screen: Screen): Boolean
}