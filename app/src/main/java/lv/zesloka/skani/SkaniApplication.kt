package lv.zesloka.skani

import android.app.Application
import timber.log.Timber

class SkaniApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}