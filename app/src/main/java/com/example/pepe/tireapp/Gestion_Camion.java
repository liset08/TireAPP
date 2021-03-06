package com.example.pepe.tireapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pepe.tireapp.Activities.MenuCamionActivity;
import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.model.Camion;
import com.example.pepe.tireapp.repositories.CamionRepository;
import com.example.pepe.tireapp.repositories.UsuarioRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gestion_Camion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText ettag, etplaca, etmodelo, etmarca, etkilometraje, etnejes, etpneto,etpbruto;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion__camion);

        inicialiteComponents();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dataNewCamion()==null){
                    Toast.makeText(Gestion_Camion.this, "Complete todos los campos" , Toast.LENGTH_LONG).show();
                }else {
                    final Camion camion = dataNewCamion();

                    ApiService api = ApiServiceGenerator.createService(ApiService.class);

                    Call call = api.create(camion);

                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Toast.makeText(Gestion_Camion.this, "REGISTRO EXITOSO", Toast.LENGTH_LONG).show();
                            finish();
                            CamionRepository.añadirCamion(camion);
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(Gestion_Camion.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });



    }

    private Camion dataNewCamion(){

        if(     ettag.getText().toString().isEmpty() || etplaca.getText().toString().isEmpty() ||
                etmodelo.getText().toString().isEmpty() || etmarca.getText().toString().isEmpty() ||
                etkilometraje.getText().toString().isEmpty() || etnejes.getText().toString().isEmpty()||
                etpneto.getText().toString().isEmpty() ||   etpbruto.getText().toString().isEmpty()){

            return null;
        }
        else{
            Camion camion = new Camion();
            camion.setTag(ettag.getText().toString());
            camion.setPlaca(etplaca.getText().toString());
            camion.setModelo(etmodelo.getText().toString());
            camion.setMarca(etmarca.getText().toString());
            camion.setKilometraje(Double.parseDouble(etkilometraje.getText().toString()));
            camion.setEjes(Integer.parseInt(etnejes.getText().toString()));
            camion.setCarga_neta(Double.parseDouble(etpneto.getText().toString()));
            camion.setCarga_util(Double.parseDouble(etpbruto.getText().toString()));
            camion.setEstado("1");
            camion.setUsureg(UsuarioRepository.getUsuario().getUsername());
            camion.setFecreg(obtenerFechaActual());
            camion.setNum_llantas(this.calcularNLlantas(camion.getEjes()));
            return camion;
        }
    }

    private void inicialiteComponents(){
        ettag = findViewById(R.id.editTextTag);
        etplaca = findViewById(R.id.editTextPlaca);
        etmodelo = findViewById(R.id.editTextModelo);
        etmarca = findViewById(R.id.editTextMarca);
        etkilometraje = findViewById(R.id.editTextKilometraje);
        etnejes = findViewById(R.id.editTextNEjes);
        etpneto = findViewById(R.id.editTextPNeto);
        etpbruto = findViewById(R.id.editTextPBruto);

        btnAgregar=findViewById(R.id.buttomAgregar);
    }

    private int calcularNLlantas(int nejes){
        int s = nejes*4-2;
        return s;
    }

    public String obtenerFechaActual(){

        //formato necesario para el datetime de sqlServer
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }

    public void Salir (View paramView){
        finish();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
