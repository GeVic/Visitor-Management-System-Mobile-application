package edu.bennett.tachyons.vms;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NAYA on 9/3/2017.
 */

public class ExitData {
    @SerializedName("success")
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
