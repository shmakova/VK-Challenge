package ru.shmakova.vk.di.modules

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import ru.shmakova.vk.di.annotations.OkHttpInterceptors
import ru.shmakova.vk.di.annotations.OkHttpNetworkInterceptors

@Module
internal object OkHttpInterceptorsModule {
    @Provides
    @OkHttpInterceptors
    @Singleton
    @JvmStatic
    fun provideOkHttpInterceptors(): List<Interceptor> = emptyList()

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    @JvmStatic
    fun provideOkHttpNetInterceptors(): List<Interceptor> = emptyList()
}
