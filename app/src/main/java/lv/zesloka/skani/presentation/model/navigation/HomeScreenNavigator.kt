package lv.zesloka.skani.presentation.model.navigation

import androidx.navigation.NavController
import lv.zesloka.skani.ui.song.HomeFragmentDirections

class HomeScreenNavigator(
    private val navController: NavController?
) : ScreenNavigator {

    override fun navigateTo(screen: Screen): Boolean {
        navController?.let {
            return when (screen) {
                Screen.LOGIN -> {
                    navController.navigate(
                        HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                    )
                    true
                }
                else -> false
            }
        }
        return false
    }

}