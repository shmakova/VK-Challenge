package ru.shmakova.vk.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.shmakova.vk.presentation.main.MainActivity

@Module
abstract class AppModule {
    @ContributesAndroidInjector
    internal abstract fun contributeActivityInjector(): MainActivity
}
