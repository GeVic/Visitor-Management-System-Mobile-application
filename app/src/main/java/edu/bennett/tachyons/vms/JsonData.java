package edu.bennett.tachyons.vms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NAYA on 8/19/2017.
 */

public class JsonData {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("data")
    private List<GetRequest> data;
    @SerializedName("errors")
    @Expose
    private Errorcodes errors;
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<GetRequest> getData() {
        return data;
    }

    public void setData(List<GetRequest> data) {
        this.data = data;
    }

    public Errorcodes getErrors() {
        return errors;
    }

    public void setErrors(Errorcodes errors) {
        this.errors = errors;
    }
}

