package com.qualityfull.reactivexandroidbyexamples.data.local.entities;

import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;
import com.qualityfull.reactivexandroidbyexamples.data.local.tables.SeriesDetailTable;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@StorIOSQLiteType(table = SeriesDetailTable.TABLE, generateTableClass = false)
public class SeriesDetail {

    @StorIOSQLiteColumn(name = SeriesDetailTable.COLUMN_ID_MOVIE_DB,  key = true)
    Integer id_movie_db;

    @StorIOSQLiteColumn(name = SeriesDetailTable.COLUMN_ID_TV_DB)
    Integer id_tv_db;

    @StorIOSQLiteColumn(name = SeriesDetailTable.COLUMN_NAME)
    String name;

    @StorIOSQLiteColumn(name = SeriesDetailTable.COLUMN_OVERVIEW)
    String overview;

    @StorIOSQLiteColumn(name = SeriesDetailTable.COLUMN_POSTER_PATH)
    String poster_path;

    @StorIOSQLiteColumn(name = SeriesDetailTable.COLUMN_SITE_RATING)
    Double siteRating;

    @StorIOSQLiteColumn(name = SeriesDetailTable.COLUMN_STATUS)
    String status;

    @StorIOSQLiteColumn(name = SeriesDetailTable.COLUMN_CHARACTERS)
    String characters;

    @StorIOSQLiteColumn(name = SeriesDetailTable.COLUMN_EPISODES)
    String episodes;

    SeriesDetail() {
    }

    private SeriesDetail(@Nullable Integer id_movie_db, @Nullable Integer id_tv_db, @Nullable String name,
                         @Nullable String overview, @Nullable String poster_path, @Nullable Double siteRating,
                         @Nullable String status, @Nullable String characters, @Nullable String episodes) {
        this.id_movie_db = id_movie_db;
        this.id_tv_db = id_tv_db;
        this.name = name;
        this.overview = overview;
        this.poster_path = poster_path;
        this.siteRating = siteRating;
        this.status = status;
        this.characters = characters;
        this.episodes = episodes;
    }

    @NonNull
    public static SeriesDetail newSeriesDetail(@Nullable Integer id_movie_db, @Nullable Integer id_tv_db, @Nullable String name,
                                               @Nullable String overview, @Nullable String poster_path, @Nullable Double siteRating,
                                               @Nullable String status, @Nullable String characters, @Nullable String episodes) {
        return new SeriesDetail(id_movie_db, id_tv_db, name, overview, poster_path, siteRating, status, characters, episodes);
    }

    @NonNull
    public Integer getId_movie_db() {
        return id_movie_db;
    }

    @NonNull
    public Integer getId_tv_db() {
        return id_tv_db;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getOverview() {
        return overview;
    }

    @NonNull
    public String getPoster_path() {
        return poster_path;
    }

    @NonNull
    public Double getSiteRating() {
        return siteRating;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    @NonNull
    public String getCharacters() {
        return characters;
    }

    @NonNull
    public String getEpisodes() {
        return episodes;
    }
}
