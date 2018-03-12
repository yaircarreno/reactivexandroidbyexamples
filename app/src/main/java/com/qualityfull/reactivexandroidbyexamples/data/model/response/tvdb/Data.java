
package com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("seriesName")
    @Expose
    private String seriesName;
    @SerializedName("aliases")
    @Expose
    private List<Object> aliases = null;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("seriesId")
    @Expose
    private String seriesId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("firstAired")
    @Expose
    private String firstAired;
    @SerializedName("network")
    @Expose
    private String network;
    @SerializedName("networkId")
    @Expose
    private String networkId;
    @SerializedName("runtime")
    @Expose
    private String runtime;
    @SerializedName("genre")
    @Expose
    private List<String> genre = null;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("lastUpdated")
    @Expose
    private Integer lastUpdated;
    @SerializedName("airsDayOfWeek")
    @Expose
    private String airsDayOfWeek;
    @SerializedName("airsTime")
    @Expose
    private String airsTime;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("imdbId")
    @Expose
    private String imdbId;
    @SerializedName("zap2itId")
    @Expose
    private String zap2itId;
    @SerializedName("added")
    @Expose
    private String added;
    @SerializedName("addedBy")
    @Expose
    private Integer addedBy;
    @SerializedName("siteRating")
    @Expose
    private Double siteRating;
    @SerializedName("siteRatingCount")
    @Expose
    private Integer siteRatingCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public List<Object> getAliases() {
        return aliases;
    }

    public void setAliases(List<Object> aliases) {
        this.aliases = aliases;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Integer lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getAirsDayOfWeek() {
        return airsDayOfWeek;
    }

    public void setAirsDayOfWeek(String airsDayOfWeek) {
        this.airsDayOfWeek = airsDayOfWeek;
    }

    public String getAirsTime() {
        return airsTime;
    }

    public void setAirsTime(String airsTime) {
        this.airsTime = airsTime;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getZap2itId() {
        return zap2itId;
    }

    public void setZap2itId(String zap2itId) {
        this.zap2itId = zap2itId;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public Integer getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }

    public Double getSiteRating() {
        return siteRating;
    }

    public void setSiteRating(Double siteRating) {
        this.siteRating = siteRating;
    }

    public Integer getSiteRatingCount() {
        return siteRatingCount;
    }

    public void setSiteRatingCount(Integer siteRatingCount) {
        this.siteRatingCount = siteRatingCount;
    }

}
