package com.qualityfull.reactivexandroidbyexamples;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import com.qualityfull.reactivexandroidbyexamples.injection.component.ApplicationComponent;
import com.qualityfull.reactivexandroidbyexamples.injection.component.DaggerApplicationComponent;
import com.qualityfull.reactivexandroidbyexamples.injection.module.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class RxByExamplesApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            setupLeakCanary();
        }
    }

    public static RxByExamplesApplication get(Context context) {
        return (RxByExamplesApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    protected void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        enabledStrictMode();
        LeakCanary.install(this);
    }

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}
