package mx.araiza.imagetest.interfaces;

import java.util.List;

import mx.araiza.imagetest.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by george on 12/10/2016.
 */

public interface TestApi {
    @FormUrlEncoded
    @POST("test.php")
    Call<Void> insertUsuario(@Field("username") String username, @Field("password") String password, @Field("email") String email);

    @GET("test2.php")
    Call<List<Usuario>> getUsuario();
}
