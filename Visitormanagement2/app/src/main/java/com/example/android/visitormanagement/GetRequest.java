package com.example.android.visitormanagement;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NAYA on 8/19/2017.
 */

public class GetRequest {
    @SerializedName("visitor_id")
    private String visitorId;
    @SerializedName("card_no")
    private String cardNo;
    @SerializedName("name")
    private String name;
    @SerializedName("entry_time")
    private String entryTime;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("purpose")
    private String purpose;

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
