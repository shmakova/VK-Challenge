package ru.shmakova.vk.di.modules;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
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
    static Gson provideGson() {
        return new GsonBuilder().setLenient().create();
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
    static Retrofit provideRetrofit(@NonNull OkHttpClient okHttpClient, @NonNull Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .validateEagerly(BuildConfig.DEBUG)
                .build();
    }
}
