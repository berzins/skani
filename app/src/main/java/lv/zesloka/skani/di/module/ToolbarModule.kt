package lv.zesloka.skani.di.module

import android.app.Activity
import dagger.Module
import dagger.Provides
import lv.zesloka.skani.ui.widgets.navbar.NavigationBarOwner

@Module(includes = [ApplicationProviderModule::class])
class ToolbarModule {

    @Provides
    fun providesNavBarOwner(currentActivity: Activity?): NavigationBarOwner =
        currentActivity as NavigationBarOwner
}