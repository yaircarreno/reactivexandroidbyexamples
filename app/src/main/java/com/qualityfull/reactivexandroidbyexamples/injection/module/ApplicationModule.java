package com.qualityfull.reactivexandroidbyexamples.injection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.impl.DefaultStorIOSQLite;
import com.qualityfull.reactivexandroidbyexamples.data.local.DbOpenHelper;
import com.qualityfull.reactivexandroidbyexamples.data.local.entities.SeriesDetail;
import com.qualityfull.reactivexandroidbyexamples.data.local.entities.SeriesDetailSQLiteTypeMapping;
import com.qualityfull.reactivexandroidbyexamples.data.remote.MarvelService;
import com.qualityfull.reactivexandroidbyexamples.data.remote.MovieDBService;
import com.qualityfull.reactivexandroidbyexamples.data.remote.TvDBService;
import com.qualityfull.reactivexandroidbyexamples.injection.qualifier.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    MarvelService provideMarvelService() {
        return MarvelService.Creator.newMarvelService();
    }

    @Provides
    @Singleton
    TvDBService provideTvDBService() {
        return TvDBService.Creator.newTvDBService();
    }

    @Provides
    @Singleton
    MovieDBService provideMovieDBService() {
        return MovieDBService.Creator.newMovieDBService();
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences() {
        return mApplication.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    @NonNull
    @Singleton
    @Provides
    StorIOSQLite provideStorIOSQLite(@NonNull SQLiteOpenHelper sqLiteOpenHelper) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(SeriesDetail.class, new SeriesDetailSQLiteTypeMapping()).build();
    }

    @NonNull
    @Singleton
    @Provides
    SQLiteOpenHelper provideSQLiteOpenHelper() {
        return new DbOpenHelper(mApplication);
    }
}
