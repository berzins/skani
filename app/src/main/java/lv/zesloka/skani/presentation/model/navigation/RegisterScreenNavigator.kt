package lv.zesloka.skani.presentation.model.navigation

import androidx.navigation.NavController
import lv.zesloka.skani.ui.auth.RegisterFragmentDirections
import timber.log.Timber

class RegisterScreenNavigator constructor(
    private val navController: NavController?
) : ScreenNavigator {
    override fun navigateTo(screen: Screen): Boolean {
        navController?.let {
            when (screen) {
                Screen.LOGIN -> {
                    val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    navController.navigate(action)
                    return true
                }
                else -> Timber.d("Register screen doesn't support navigation to $screen")
            }
        }
        return false
    }
}