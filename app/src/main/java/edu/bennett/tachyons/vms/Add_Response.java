package edu.bennett.tachyons.vms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NAYA on 7/14/2017.
 */

public class Add_Response {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("visitor_id")
    @Expose
    private String visitorId;
    @SerializedName("errors")
    @Expose
    private Errorcodes errors;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public Errorcodes getErrors() {
        return errors;
    }

    public void setErrors(Errorcodes errors) {
        this.errors = errors;
    }

}
