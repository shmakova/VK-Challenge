package ru.shmakova.vk

import android.app.Activity
import android.app.Application
import com.vk.sdk.VKSdk
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ru.shmakova.vk.di.AppComponent
import ru.shmakova.vk.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    private lateinit var appComponent: AppComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initLogger()
        VKSdk.initialize(this);
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
