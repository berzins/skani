package lv.zesloka.skani.presentation.model.navigation

import android.app.Activity
import androidx.navigation.Navigation
import lv.zesloka.skani.R
import lv.zesloka.skani.ui.splash.SplashFragmentDirections
import timber.log.Timber

class SplashScreenNavigator(
    private val currentActivity: Activity?
) : ScreenNavigator {
    override fun navigateTo(screen: Screen): Boolean {
        currentActivity?.let {
            when (screen) {
                Screen.LOGIN -> {
                    val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                    Navigation.findNavController(it, R.id.navigation).navigate(action)
                    return true
                }
                Screen.HOME -> {
                    val action = SplashFragmentDirections.actionSplashFragmentToSongListFragment()
                    Navigation.findNavController(it, R.id.navigation).navigate(action)
                    return true
                }
                else -> {
                    Timber.d("Splash screen doesn't support navigation to $screen")
                }
            }

        }
        return false
    }

}