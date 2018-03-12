package com.qualityfull.reactivexandroidbyexamples.data.local.tables;

import android.support.annotation.NonNull;

public class SeriesDetailTable {

    @NonNull
    public static final String TABLE = "series";

    @NonNull
    public static final String COLUMN_ID_MOVIE_DB = "id_movie_db";

    @NonNull
    public static final String COLUMN_ID_TV_DB = "id_tv_db";

    @NonNull
    public static final String COLUMN_NAME = "name";

    @NonNull
    public static final String COLUMN_OVERVIEW = "overview";

    @NonNull
    public static final String COLUMN_POSTER_PATH = "poster_path";

    @NonNull
    public static final String COLUMN_SITE_RATING = "siteRating";

    @NonNull
    public static final String COLUMN_STATUS = "status";

    @NonNull
    public static final String COLUMN_CHARACTERS = "characters";

    @NonNull
    public static final String COLUMN_EPISODES = "episodes";

    private SeriesDetailTable() {
        throw new IllegalStateException("No instances please");
    }

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_ID_MOVIE_DB + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_ID_TV_DB + " INTEGER NOT NULL, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_OVERVIEW + " TEXT NOT NULL, "
                + COLUMN_POSTER_PATH + " TEXT NOT NULL, "
                + COLUMN_SITE_RATING + " DOUBLE NOT NULL, "
                + COLUMN_STATUS + " TEXT NOT NULL, "
                + COLUMN_CHARACTERS + " TEXT NOT NULL, "
                + COLUMN_EPISODES + " TEXT NOT NULL"
                + ");";
    }
}
