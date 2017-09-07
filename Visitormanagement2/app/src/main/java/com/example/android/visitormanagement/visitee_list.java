package com.example.android.visitormanagement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NAYA on 8/8/2017.
 */

public class visitee_list {

    @SerializedName("visitee_no")
    @Expose
    private String visiteeNo;
    @SerializedName("name")
    @Expose
    private String name;

    public String getVisiteeNo() {
        return visiteeNo;
    }

    public void setVisiteeNo(String visiteeNo) {
        this.visiteeNo = visiteeNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
