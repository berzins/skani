package lv.zesloka.skani.di.module

import android.app.Activity
import android.app.Application
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.Module
import dagger.Provides
import lv.zesloka.skani.SkaniApplication

@Module
class ApplicationProviderModule constructor(private val application: Application) {

    @Provides
    fun providesApplication(): Application = application

    @Provides
    fun providesCurrentActivity(): Activity? = (application as SkaniApplication).getCurrentActivity()

}