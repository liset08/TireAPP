package com.example.pepe.tireapp.Activities;


import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.adapters.CamionListAdpater;
import com.example.pepe.tireapp.model.Camion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuAuditoriaActivity extends AppCompatActivity {

    private static final String TAG = MenuAuditoriaActivity.class.getSimpleName();
    private EditText editTextCamionplaca;
    private Button buscarCamionAuditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_auditoria);

        editTextCamionplaca = findViewById(R.id.sviewFindCamionAuditor);
        buscarCamionAuditor = findViewById(R.id.btnBuscarCamionAuditoria);

        camionList = findViewById(R.id.recyclerviewCamionAuditor);
        camionList.setLayoutManager(new LinearLayoutManager(this));
        camionList.setAdapter(new CamionListAdpater(this));

        initialize();

        buscarCamionAuditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findCamionAuditor();
            }
        });

    }

    private void findCamionAuditor(){

        String buscar = editTextCamionplaca.getText().toString();

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Camion> call = service.findCamion(buscar);

        call.enqueue(new Callback<Camion>() {
            @Override
            public void onResponse(Call<Camion> call, Response<Camion> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);
                    //Toast.makeText(MenuCamionActivity.this, statusCode, Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {

                        String placa = response.body().getPlaca();
                        int ejes = response.body().getEjes();
                        Intent intent = new Intent(MenuAuditoriaActivity.this, InfCamionActivity.class);
                        intent.putExtra("placa",  placa);
                        intent.putExtra("ejes" , ejes);
                        intent.putExtra("tipo" , "auditor");
                        startActivity(intent);

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MenuAuditoriaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }

            }

            @Override
            public void onFailure(Call<Camion> call, Throwable t) {
                try {
                    Log.e(TAG, "onThrowable: " + t.toString(), t);
                    Toast.makeText(MenuAuditoriaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }catch (Throwable x){}
            }
        });


    }


    private RecyclerView camionList;

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Camion>> call = service.getCamiones();

        call.enqueue(new Callback<List<Camion>>() {
            @Override
            public void onResponse(Call<List<Camion>> call, Response<List<Camion>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Camion> camiones = response.body();
                        Log.d(TAG, "camiones: " + camiones);
                        Collections.reverse(camiones);

                        CamionListAdpater adapter = (CamionListAdpater) camionList.getAdapter();
                        adapter.setCamiones(camiones);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MenuAuditoriaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }



            @Override
            public void onFailure(Call<List<Camion>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MenuAuditoriaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }



}
