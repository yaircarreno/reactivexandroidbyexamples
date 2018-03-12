
package com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSeries {

    @SerializedName("data")
    @Expose
    private List<Series> data = null;

    public List<Series> getData() {
        return data;
    }

    public void setData(List<Series> data) {
        this.data = data;
    }

}
