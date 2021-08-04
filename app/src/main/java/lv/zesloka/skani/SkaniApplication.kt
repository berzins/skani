package lv.zesloka.skani

import android.app.Activity
import android.app.Application
import android.os.Bundle
import lv.zesloka.skani.di.AppComponent
import lv.zesloka.skani.di.module.ApplicationProviderModule
import lv.zesloka.skani.di.DaggerAppComponent

import timber.log.Timber

class SkaniApplication : Application() {

    private lateinit var mAppComponent: AppComponent
    private var currentActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()

        mAppComponent = DaggerAppComponent.builder()
            .applicationProviderModule(
                ApplicationProviderModule(
                    this
                )
            )
            .build()
        mAppComponent.inject(this)

        initTimber()

        updateCurrentActivity()

    }

    fun getCurrentActivity() = currentActivity

    private fun updateCurrentActivity() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
//                currentActivity = null
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                currentActivity = activity
            }

            override fun onActivityResumed(activity: Activity) {
            }

        })
    }

    public fun getAppComponent(): AppComponent = mAppComponent

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}