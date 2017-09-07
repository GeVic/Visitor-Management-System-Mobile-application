package com.example.android.visitormanagement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NAYA on 8/10/2017.
 */

public class Jsonerror {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("errors")
    @Expose
    private Errorcodes errors;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Errorcodes getErrors() {
        return errors;
    }

    public void setErrors(Errorcodes errors) {
        this.errors = errors;
    }

}
