package ru.shmakova.vk

import android.app.Application
import com.vk.sdk.VKSdk
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
        VKSdk.initialize(this);
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
