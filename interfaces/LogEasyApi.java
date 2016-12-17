package mx.araiza.imagetest.interfaces;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by george on 12/10/2016.
 */

public interface LogEasyApi {
    @FormUrlEncoded
    @POST("connection.php")
    Call<Void> insertPhoto(@Field("encoded_string") String encoded_string, @Field("image_name") String image_name);

}
