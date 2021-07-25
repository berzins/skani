package lv.zesloka.skani.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationProviderModule constructor(private val application: Application) {

    @Provides
    fun providesApplication(): Application = application
}