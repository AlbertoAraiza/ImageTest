package mx.araiza.imagetest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by george on 12/10/2016.
 */

public class TokenResponse {
    @SerializedName("encoded_string")
    @Expose
    private String encoded_string;
    @SerializedName("image_name")
    @Expose
    private String image_name;

    public String getEncoded_string() {
        return encoded_string;
    }

    public String getImage_name() {
        return image_name;
    }
}
