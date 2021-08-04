package lv.zesloka.skani.presentation.model.navigation

import android.app.Activity
import androidx.navigation.Navigation
import lv.zesloka.skani.R
import lv.zesloka.skani.ui.auth.LoginFragmentDirections
import timber.log.Timber

class LoginScreenNavigator(
    private val currentActivity: Activity?
): ScreenNavigator {

    override fun navigateTo(screen: Screen): Boolean {
        currentActivity?.let {
            when (screen) {
                Screen.REGISTER -> {

                    val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                    Navigation.findNavController(it, R.id.navigation).navigate(action)
                    return true
                }
                Screen.HOME -> {
                    val action = LoginFragmentDirections.actionLoginFragmentToSongListFragment()
                    Navigation.findNavController(it, R.id.navigation).navigate(action)
                    return true
                }
                else -> {
                    Timber.d("Login screen doesn't support navigation to $screen")
                }
            }
        }
        return false
    }
}