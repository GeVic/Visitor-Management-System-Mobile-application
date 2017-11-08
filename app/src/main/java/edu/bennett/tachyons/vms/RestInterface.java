package edu.bennett.tachyons.vms;


import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by NAYA on 7/10/2017.
 */

public interface RestInterface {
    BaseUrl bu = new BaseUrl();

    String url = bu.BASE_URL + "v1/oauth/index.php";
    @FormUrlEncoded
    @POST("/v1/oauth/")
    void Login(@Field("username") String user,
               @Field("password") String pass, Callback<JsonResponse> cb);



}
