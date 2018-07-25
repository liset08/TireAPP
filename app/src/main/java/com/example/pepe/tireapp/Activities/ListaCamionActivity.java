package com.example.pepe.tireapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.adapters.CamionListAdpater;
import com.example.pepe.tireapp.model.Grupoempresa;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaCamionActivity extends AppCompatActivity {

    private static final String TAG = ListaCamionActivity.class.getSimpleName();
    private RecyclerView camionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_camion);

        camionList = findViewById(R.id.recyclerview);
        camionList.setLayoutManager(new LinearLayoutManager(this));

        camionList.setAdapter(new CamionListAdpater());

        initialize();

    }

    private void initialize() {
        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Grupoempresa>> call = service.getGemp();

        call.enqueue(new Callback<List<Grupoempresa>>() {
            @Override
            public void onResponse(Call<List<Grupoempresa>> call, Response<List<Grupoempresa>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Grupoempresa> productos = response.body();
                        Log.d(TAG, "productos: " + productos);

                        CamionListAdpater adapter = (CamionListAdpater) camionList.getAdapter();
                        adapter.setGrupoempresa(productos);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(ListaCamionActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }



            @Override
            public void onFailure(Call<List<Grupoempresa>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(ListaCamionActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}
