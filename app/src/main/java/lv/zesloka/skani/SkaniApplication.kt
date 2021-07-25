package lv.zesloka.skani

import android.app.Application
import lv.zesloka.skani.di.AppComponent
import lv.zesloka.skani.di.ApplicationProviderModule
import lv.zesloka.skani.di.DaggerAppComponent

import timber.log.Timber

class SkaniApplication : Application() {

    private lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        mAppComponent = DaggerAppComponent.builder()
            .applicationProviderModule(ApplicationProviderModule(this))
            .build()
        mAppComponent.inject(this)

        initTimber()
    }

    public fun getAppComponent(): AppComponent = mAppComponent

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}