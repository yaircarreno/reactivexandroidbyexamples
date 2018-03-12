
package com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode {

    @SerializedName("absoluteNumber")
    @Expose
    private Integer absoluteNumber;
    @SerializedName("airedEpisodeNumber")
    @Expose
    private Integer airedEpisodeNumber;
    @SerializedName("airedSeason")
    @Expose
    private Integer airedSeason;
    @SerializedName("airedSeasonID")
    @Expose
    private Integer airedSeasonID;
    @SerializedName("dvdEpisodeNumber")
    @Expose
    private Double dvdEpisodeNumber;
    @SerializedName("dvdSeason")
    @Expose
    private Integer dvdSeason;
    @SerializedName("episodeName")
    @Expose
    private String episodeName;
    @SerializedName("firstAired")
    @Expose
    private String firstAired;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("language")
    @Expose
    private Language language;
    @SerializedName("lastUpdated")
    @Expose
    private Integer lastUpdated;
    @SerializedName("overview")
    @Expose
    private String overview;

    public Integer getAbsoluteNumber() {
        return absoluteNumber;
    }

    public void setAbsoluteNumber(Integer absoluteNumber) {
        this.absoluteNumber = absoluteNumber;
    }

    public Integer getAiredEpisodeNumber() {
        return airedEpisodeNumber;
    }

    public void setAiredEpisodeNumber(Integer airedEpisodeNumber) {
        this.airedEpisodeNumber = airedEpisodeNumber;
    }

    public Integer getAiredSeason() {
        return airedSeason;
    }

    public void setAiredSeason(Integer airedSeason) {
        this.airedSeason = airedSeason;
    }

    public Integer getAiredSeasonID() {
        return airedSeasonID;
    }

    public void setAiredSeasonID(Integer airedSeasonID) {
        this.airedSeasonID = airedSeasonID;
    }

    public Double getDvdEpisodeNumber() {
        return dvdEpisodeNumber;
    }

    public void setDvdEpisodeNumber(Double dvdEpisodeNumber) {
        this.dvdEpisodeNumber = dvdEpisodeNumber;
    }

    public Integer getDvdSeason() {
        return dvdSeason;
    }

    public void setDvdSeason(Integer dvdSeason) {
        this.dvdSeason = dvdSeason;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Integer lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

}
