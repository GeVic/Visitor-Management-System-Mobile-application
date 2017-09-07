package com.example.android.visitormanagement;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by NAYA on 8/8/2017.
 */

public interface GetVisitee {
    String Visitee_url = "https://congruent-multisyst.000webhostapp.com/v1/get/visitee.php";
    @GET("/v1/get/visitee.php")
    void getDetails(Callback<json_visitee> cb);
}
