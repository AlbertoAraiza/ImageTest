package mx.araiza.imagetest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by george on 12/9/2016.
 */

public class AddBussinessActivity extends AppCompatActivity {
    private EditText nombre, calle, numero, colonia, municipio, estado, codigoPostal, horarios, telefono, coordenadas;
    private Button add;
    private final String ip_address = "10.0.0.4";
    private final String php_address = ip_address+"/php/connection.php";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbussiness);
        nombre = (EditText) findViewById(R.id.etNombre);
        calle = (EditText) findViewById(R.id.etCalle);
        numero = (EditText) findViewById(R.id.etNumero);
        colonia = (EditText) findViewById(R.id.etColonia);
        municipio = (EditText) findViewById(R.id.etMunicipio);
        estado = (EditText) findViewById(R.id.etEstado);
        codigoPostal = (EditText)findViewById(R.id.etCodigoPostal);
        horarios = (EditText) findViewById(R.id.etHorarios);
        telefono = (EditText) findViewById(R.id.etTelefono);
        coordenadas = (EditText) findViewById(R.id.etCoordenadas);
        add = (Button) findViewById(R.id.btnAddBussiness);
    }
}
