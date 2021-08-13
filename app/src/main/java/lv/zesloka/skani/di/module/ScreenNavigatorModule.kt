package lv.zesloka.skani.di.module

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.Module
import dagger.Provides
import lv.zesloka.skani.R
import lv.zesloka.skani.di.qualifiyer.*
import lv.zesloka.skani.presentation.model.navigation.*
import lv.zesloka.skani.presentation.redux.ActionDispatcher
import javax.inject.Singleton

@Module(includes = [ApplicationProviderModule::class])
class ScreenNavigatorModule {


    @Provides
    fun providesNavController(activity: Activity?): NavController? =
        activity?.let {
            Navigation.findNavController(it, R.id.navigation)
        }

    @Provides
    @Singleton
    fun providesAppNavigation(
        @QSplashNavigator splashScreenNavigator: ScreenNavigator,
        @QLoginNavigator loginScreenNavigator: ScreenNavigator,
        @QRegisterNavigator registerScreenNavigator: ScreenNavigator,
        @QHomeScreenNavigator homeScreenNavigator: ScreenNavigator
    ): ApplicationNavigation =
        AppNavigation(
            mapOf(
                Screen.SPLASH to splashScreenNavigator,
                Screen.LOGIN to loginScreenNavigator,
                Screen.REGISTER to registerScreenNavigator,
                Screen.HOME to homeScreenNavigator
            )
        )

    @Provides
    @QDefaultAppNavigators
    fun provideNonsenseString(): String = "blah blah"

    @Provides
    @QSplashNavigator
    fun provideSplashScreenNavigator(currentActivity: Activity?): ScreenNavigator =
        SplashScreenNavigator(currentActivity)

    @Provides
    @QLoginNavigator
    fun providesLoginScreenNavigator(currentActivity: Activity?): ScreenNavigator =
        LoginScreenNavigator(currentActivity)

    @Provides
    @QRegisterNavigator
    fun providesRegisterScreenNavigator(navController: NavController?): ScreenNavigator =
        RegisterScreenNavigator(navController)

    @Provides
    @QHomeScreenNavigator
    fun providesHomeScreenNavigator(navController: NavController?): ScreenNavigator =
        HomeScreenNavigator(navController)


}