package lv.zesloka.skani.presentation.model.navigation

interface ApplicationNavigation {
    fun getNavigator(screen: Screen): ScreenNavigator?
}

class AppNavigation(private val navigators: Map<Screen, ScreenNavigator>): ApplicationNavigation {
    override fun getNavigator(screen: Screen): ScreenNavigator? {
        return navigators[screen]
    }

}