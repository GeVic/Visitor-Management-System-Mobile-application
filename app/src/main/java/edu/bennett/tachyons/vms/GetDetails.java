package edu.bennett.tachyons.vms;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by NAYA on 8/19/2017.
 */


public interface GetDetails {

    BaseUrl bu = new BaseUrl();
    String BASE_URL = bu.BASE_URL;

    @GET("/v1/get/")
    void get_detail(Callback<JsonData> getJSON);
    @GET("/v1/exit/{visitor_id}")
    void exit_user(@Path("visitor_id") String visitor_id, Callback<ExitData> getJson);

}
