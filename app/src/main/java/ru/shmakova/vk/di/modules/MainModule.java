package ru.shmakova.vk.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.vk.App;

@Module
public class MainModule {
    @Provides
    @NonNull
    @Singleton
    static Context provideApplicationContext(@NonNull App app) {
        return app.getApplicationContext();
    }
}
