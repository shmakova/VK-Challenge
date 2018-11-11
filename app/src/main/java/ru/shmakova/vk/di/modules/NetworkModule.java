package ru.shmakova.vk.di.modules;

import android.support.annotation.NonNull;

import com.squareup.moshi.Moshi;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import ru.shmakova.vk.BuildConfig;
import ru.shmakova.vk.data.network.VkApiService;
import ru.shmakova.vk.di.annotations.OkHttpInterceptors;
import ru.shmakova.vk.di.annotations.OkHttpNetworkInterceptors;

@Module
public class NetworkModule {
    @Provides
    @NonNull
    @Singleton
    static OkHttpClient provideOkHttpClient(@OkHttpInterceptors @NonNull List<Interceptor> interceptors,
                                            @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }

        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        return okHttpBuilder.build();
    }

    @Provides
    @NonNull
    @Singleton
    static Moshi provideMoshi() {
        return new Moshi.Builder().build();
    }

    @Provides
    @NonNull
    @Singleton
    static VkApiService provideVkApiService(@NonNull Retrofit retrofit) {
        return retrofit.create(VkApiService.class);
    }

    @NonNull
    @Provides
    @Singleton
    static Retrofit provideRetrofit(@NonNull OkHttpClient okHttpClient, @NonNull Moshi moshi) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .validateEagerly(BuildConfig.DEBUG)
                .build();
    }
}
