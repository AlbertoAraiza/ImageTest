package mx.araiza.imagetest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import mx.araiza.imagetest.interfaces.LogEasyApi;
import mx.araiza.imagetest.interfaces.TestApi;
import mx.araiza.imagetest.models.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button button, addBussiness, addUsuario;
    private ImageView foto;
    private String encoded_string, image_name;
    private File file;
    private Bitmap bitmap;
    private Uri file_uri;
    private final String ip_address = "10.0.0.4";
    private final String php_address = ip_address+"/php/connection.php";
    int MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE;
    EditText username, password, email;
    Usuario uss = new Usuario();
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.start);
        foto = (ImageView) findViewById(R.id.ivFoto);
        addBussiness = (Button) findViewById(R.id.btnAddBussiness);
        username = (EditText) findViewById(R.id.etUss);
        password = (EditText) findViewById(R.id.etPass);
        email = (EditText) findViewById(R.id.etMail);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://picou.000webhostapp.com/php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TestApi service2 = retrofit.create(TestApi.class);
        Call<List<Usuario>> call2 = service2.getUsuario();
        call2.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                List<Usuario> usuarios = response.body();
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
            }
        });

        addUsuario = (Button) findViewById(R.id.addUss);
        addUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uss.setUsername(username.getText().toString());
                uss.setEmail(email.getText().toString());
                uss.setPassword(password.getText().toString());

                TestApi service = retrofit.create(TestApi.class);

                Call<Void> call = service.insertUsuario(uss.getUsername(), uss.getPassword(), uss.getEmail());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(MainActivity.this, "Respuesta: "+ response.message(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error: "+ t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                        || (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))

                {
                    ActivityCompat.requestPermissions
                            (MainActivity.this, new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE);
                }
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getFilesUri();
                i.putExtra(MediaStore.EXTRA_OUTPUT,file_uri);
                startActivityForResult(i,0);
            }
        });

        Picasso.with(foto.getContext()).load("https://picou.000webhostapp.com/images/testing1234.jpg").into(foto);
//        addBussiness.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


    }
    private void getFilesUri(){
        image_name = "testing1234.jpg";
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
        File.separator + image_name);
        file_uri = Uri.fromFile(file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK){

            new Encode_image().execute();
        }
    }

    private class Encode_image extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            bitmap = BitmapFactory.decodeFile(file_uri.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array,0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            makeRequest();
        }
    }
    private void makeRequest(){

        LogEasyApi service = retrofit.create(LogEasyApi.class);

        Call<Void> call = service.insertPhoto(encoded_string, image_name);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                ImageView imagen = (ImageView) findViewById(R.id.ivFoto);
                Picasso.with(imagen.getContext())
                        .load("http://10.0.0.4/tutorial/images/testing1234.jpg")
                        .placeholder(R.mipmap.test)
                        .fit().centerCrop()
//                .resize(screen.getWidth(), screen.getHeight())
                        .error(R.mipmap.ic_launcher)
                        .into(imagen);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
