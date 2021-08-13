package lv.zesloka.skani.di

import dagger.Component
import dagger.android.AndroidInjector
import lv.zesloka.skani.SkaniApplication
import lv.zesloka.skani.di.module.*
import lv.zesloka.skani.presentation.vm.HomeViewModel
import lv.zesloka.skani.presentation.vm.RegistrationViewModel
import lv.zesloka.skani.presentation.vm.SongViewModel
import lv.zesloka.skani.presentation.vm.SplashViewModel
import lv.zesloka.skani.presentation.vm.auth.LoginViewModel
import lv.zesloka.skani.ui.auth.LoginFragment
import lv.zesloka.skani.ui.auth.RegisterFragment
import lv.zesloka.skani.ui.song.HomeFragment
import lv.zesloka.skani.ui.splash.SplashFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UserModule::class,
        StoreModule::class,
        ScreenNavigatorModule::class,
        ToolbarModule::class,
        ValidationModule::class,
        ResourceProviderModule::class
    ]
)
interface AppComponent : AndroidInjector<SkaniApplication> {
    fun inject(vm: SplashViewModel)
    fun inject(vm: SongViewModel)
    fun inject(vm: LoginViewModel)
    fun inject(loginFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)
    fun inject(vm: RegistrationViewModel)
    fun inject(homeFragment: HomeFragment)
    fun inject(vm: HomeViewModel)
    fun inject(splashFragment: SplashFragment)

}