package com.example.android.visitormanagement;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Part;

/**
 * Created by NAYA on 8/19/2017.
 */

public interface GetDetails {
    public static final String BASE_URL = "https://congruent-multisyst.000webhostapp.com/";
    @GET("/v1/get/")
    void get_detail(Callback<JsonData> getJSON);
    @GET("/v1/exit/")
    void exit_user(@Part("visitor_id") String visitorid);
}
