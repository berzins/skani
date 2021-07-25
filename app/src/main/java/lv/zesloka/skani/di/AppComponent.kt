package lv.zesloka.skani.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import lv.zesloka.skani.SkaniApplication
import lv.zesloka.skani.presentation.vm.SongViewModel
import lv.zesloka.skani.presentation.vm.SplashViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [UserModule::class])
interface AppComponent: AndroidInjector<SkaniApplication>{
    fun inject(vm: SplashViewModel)
    fun inject(vm: SongViewModel)

}