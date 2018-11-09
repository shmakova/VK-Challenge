package ru.shmakova.vk.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.shmakova.vk.App
import ru.shmakova.vk.di.modules.AppModule
import ru.shmakova.vk.di.modules.MainModule
import ru.shmakova.vk.di.modules.NetworkModule
import ru.shmakova.vk.di.modules.OkHttpInterceptorsModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        AppModule::class,
        MainModule::class,
        NetworkModule::class,
        OkHttpInterceptorsModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)
}
