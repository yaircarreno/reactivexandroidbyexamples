package com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceErrorTvDB {

    @SerializedName("Error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
