
package com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episodes {

    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("data")
    @Expose
    private List<Episode> data = null;

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public List<Episode> getData() {
        return data;
    }

    public void setData(List<Episode> data) {
        this.data = data;
    }

}
