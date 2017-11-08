package edu.bennett.tachyons.vms;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by NAYA on 7/14/2017.
 */

public interface UserDetails {
    BaseUrl bu = new BaseUrl();

    // method url for adding the visitor
    String BASE_URL = bu.BASE_URL + "v1/add/index.php";

    // multipart request
    @Multipart
    @POST("/v1/add/index.php/")
    void user_details(@Part("cardno") String card_no,
                      @Part("name") String name,
                      @Part("mobile") String mobile,
                      @Part("purpose") String purpose,
                      @Part("image") TypedFile image,
                      @Part("access_token") String token,
                      @Part("visitee_no") String visiteeno,
                      @Part("org") String org,
                      @Part("address") String address,
                      Callback<Add_Response> cb);

}
