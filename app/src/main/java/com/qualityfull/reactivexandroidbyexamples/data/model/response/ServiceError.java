
package com.qualityfull.reactivexandroidbyexamples.data.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import android.support.annotation.Nullable;

public class ServiceError {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode = null;

    @SerializedName("status_message")
    @Expose
    private String statusMessage = null;

    @Nullable
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

    @SerializedName("Error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
