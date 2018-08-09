package com.example.pepe.tireapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pepe.tireapp.Activities.NeumaticoListActivity;
import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.model.Camion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarNeumaticoxCamionActivity extends AppCompatActivity {

    private static final String TAG =BuscarNeumaticoxCamionActivity.class.getSimpleName() ;
    private EditText placaText;
    Integer id_camion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_neumaticox_camion);

        placaText = (EditText) findViewById(R.id.etxt_placa_camion);

    }
    /*public void calllist (View view) {
        final String placa= placaText.getText().toString();
        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<Camion> call = service.getCamiones();

        call.enqueue(new Callback<Camion>() {
            @Override
            public void onResponse(Call<Camion> call, Response<Camion> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        Camion camions = response.body();
                        Log.d(TAG, "camions: " + camions);
                        id_camion=  camions.getCamion_ID();
                        Log.d(TAG, "camions: " + id_camion);

                    /*    Intent intent=new Intent(BuscarNeumaticoxCamionActivity.this,NeumaticoListActivity.class);
                        intent.putExtra("Id_camion", camions.getCamion_ID());
                        startActivity(intent);

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());

                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(BuscarNeumaticoxCamionActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<Camion> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(BuscarNeumaticoxCamionActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }*/
}
