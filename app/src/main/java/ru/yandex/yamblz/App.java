package ru.yandex.yamblz;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import ru.yandex.yamblz.db.TranslationsDatabase;
import ru.yandex.yamblz.db.WordFetcher;
import ru.yandex.yamblz.developer_settings.DevMetricsProxy;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModel;
import ru.yandex.yamblz.rules.Word;
import timber.log.Timber;

public class App extends Application {
    private ApplicationComponent applicationComponent;

    public TranslationsDatabase getTranslationsDb() {
        return translationsDb;
    }

    private TranslationsDatabase translationsDb;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static App get(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

//    public TranslationsDatabase getTranslationsDb() {
//        return translationsDb;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = prepareApplicationComponent().build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());

            DeveloperSettingsModel developerSettingModel = applicationComponent.developerSettingModel();
            developerSettingModel.apply();

            DevMetricsProxy devMetricsProxy = applicationComponent.devMetricsProxy();
            devMetricsProxy.apply();
        }

        translationsDb = new TranslationsDatabase(this.getApplicationContext());

        WordFetcher.loadDataToDatabase(getApplicationContext());
    }

    @NonNull
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this));
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}
