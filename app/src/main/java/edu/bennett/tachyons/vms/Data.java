package edu.bennett.tachyons.vms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NAYA on 7/12/2017.
 */

public class Data {
    @SerializedName("access_token")
    @Expose
    private String access_token;
    @SerializedName("expiry")
    @Expose
    private Integer expiry;

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String accessToken) {
        this.access_token = accessToken;
    }

    public Integer getExpiry() {
        return expiry;
    }

    public void setExpiry(Integer expiry) {
        this.expiry = expiry;
    }
}
