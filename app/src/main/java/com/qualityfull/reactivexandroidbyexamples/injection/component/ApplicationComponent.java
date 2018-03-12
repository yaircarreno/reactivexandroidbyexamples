package com.qualityfull.reactivexandroidbyexamples.injection.component;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.qualityfull.reactivexandroidbyexamples.data.DataManager;
import com.qualityfull.reactivexandroidbyexamples.data.RxOperations;
import com.qualityfull.reactivexandroidbyexamples.data.model.Pager;
import com.qualityfull.reactivexandroidbyexamples.injection.module.ApplicationModule;
import com.qualityfull.reactivexandroidbyexamples.injection.qualifier.ApplicationContext;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext Context context();

    Application application();
    SharedPreferences sharedPreferences();
    DataManager dataManager();
    RxOperations rxOperations();
    Pager pager();

    @NonNull
    StorIOSQLite storIOSQLite();
}
