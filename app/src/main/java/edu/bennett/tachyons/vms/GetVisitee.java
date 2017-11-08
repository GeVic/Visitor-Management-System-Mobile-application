package edu.bennett.tachyons.vms;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by NAYA on 8/8/2017.
 */

public interface GetVisitee {
    BaseUrl bu = new BaseUrl();

    String Visitee_url = bu.BASE_URL + "v1/get/visitee.php";

    // Get request to the server
    @GET("/v1/get/visitee.php")
    void getDetails(Callback<json_visitee> cb);
}
