package com.example.pepe.tireapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pepe.tireapp.Activities.AddActivity;
import com.example.pepe.tireapp.Activities.InfCamionActivity;
import com.example.pepe.tireapp.model.Camion;
import com.example.pepe.tireapp.repositories.CamionRepository;

public class CambioSearchActivity extends AppCompatActivity {

    private EditText editesatdo;
    private static final int REGISTER_FORM_REQUEST = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_search);
        editesatdo=(EditText) findViewById(R.id.sviewFindCamionCambio);

    }
    public void findCamionCambio(View view){

        String buscar = editesatdo.getText().toString();

        Camion camion = CamionRepository.buscarCamionbyPlaca(buscar);

        if(camion == null){
            Toast.makeText(CambioSearchActivity.this, "Camión no encontrado", Toast.LENGTH_LONG).show();
        }else{
            int idPlaca = camion.getCamion_ID();
            String placa = camion.getPlaca();
            int ejes = camion.getEjes();
            Intent intent = new Intent(CambioSearchActivity.this, CambioNeuaticoActivity.class);
            intent.putExtra("placa",  placa);
            intent.putExtra("ejes" , ejes);
            intent.putExtra("idPlaca" , idPlaca);
            startActivity(intent);
        }}
}
