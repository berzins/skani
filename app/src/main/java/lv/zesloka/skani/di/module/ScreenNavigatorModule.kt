package lv.zesloka.skani.di.module

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.Module
import dagger.Provides
import lv.zesloka.skani.R
import lv.zesloka.skani.di.qualifiyer.QDefaultAppNavigators
import lv.zesloka.skani.di.qualifiyer.QLoginNavigator
import lv.zesloka.skani.di.qualifiyer.QRegisterNavigator
import lv.zesloka.skani.di.qualifiyer.QSplashNavigator
import lv.zesloka.skani.presentation.model.navigation.*
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
        @QRegisterNavigator registerScreenNavigator: ScreenNavigator
    ): ApplicationNavigation =
        AppNavigation(
            mapOf(
                Screen.SPLASH to splashScreenNavigator,
                Screen.LOGIN to loginScreenNavigator,
                Screen.REGISTER to registerScreenNavigator
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


}