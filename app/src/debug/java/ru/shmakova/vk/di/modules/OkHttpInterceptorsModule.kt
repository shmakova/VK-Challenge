package ru.shmakova.vk.di.modules

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import ru.shmakova.vk.di.annotations.OkHttpInterceptors
import ru.shmakova.vk.di.annotations.OkHttpNetworkInterceptors
import javax.inject.Singleton

@Module
internal object OkHttpInterceptorsModule {
    @Provides
    @Singleton
    @JvmStatic
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @OkHttpInterceptors
    @Singleton
    @JvmStatic
    fun provideOkHttpInterceptors(httpLoggingInterceptor: HttpLoggingInterceptor): List<Interceptor> {
        return listOf(httpLoggingInterceptor)
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    @JvmStatic
    fun provideOkHttpNetworkInterceptors(): List<Interceptor> {
        return listOf(StethoInterceptor())
    }
}
