package com.example.android.visitormanagement;

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

//    public DataJson(Boolean success, List<Datum> data) {
//        this.success = success;
//        this.data = data;
//    }

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
}

