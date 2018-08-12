package com.example.pepe.tireapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pepe.tireapp.Gestion_Camion;
import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.adapters.CamionListAdpater;
import com.example.pepe.tireapp.adapters.NeumaticoListAdapter;
import com.example.pepe.tireapp.model.Grupoempresa;
import com.example.pepe.tireapp.model.TipoNeumatico;
import com.example.pepe.tireapp.repositories.CamionRepository;
import com.example.pepe.tireapp.repositories.NeumaticoRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NeumaticoListActivity extends AppCompatActivity {

    private static final String TAG = NeumaticoListActivity.class.getSimpleName();
    private RecyclerView neumaticoList;
    private static final int REGISTER_FORM_REQUEST = 100;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REGISTER_FORM_REQUEST) {
            //Refresh del intent
            iniciar();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neumatico_list);



        iniciar();




    }

    public void iniciar(){
        neumaticoList = findViewById(R.id.recyclerview);
        neumaticoList.setLayoutManager(new LinearLayoutManager(this));

        neumaticoList.setAdapter(new NeumaticoListAdapter(this));


        NeumaticoListAdapter adapter = (NeumaticoListAdapter) neumaticoList.getAdapter();
        adapter.setTipoNeumatico(NeumaticoRepository.getList());
        adapter.notifyDataSetChanged();

    }

    public void showRegisterdenuncia(View view){

        Intent intent = new Intent(NeumaticoListActivity.this, GestionNeumatico.class);
        startActivityForResult(intent, 100);
        neumaticoList.removeAllViews();

    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<TipoNeumatico>> call = service.getNeum();

        call.enqueue(new Callback<List<TipoNeumatico>>() {
            @Override
            public void onResponse(Call<List<TipoNeumatico>> call, Response<List<TipoNeumatico>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<TipoNeumatico> tneumatico = response.body();
                        Log.d(TAG, "productos: " + tneumatico);


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(NeumaticoListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }



            @Override
            public void onFailure(Call<List<TipoNeumatico>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(NeumaticoListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }


}
