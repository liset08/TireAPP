package com.example.pepe.tireapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.model.Camion;
import com.example.pepe.tireapp.model.Grupoempresa;
import com.example.pepe.tireapp.repositories.CamionRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = AddActivity.class.getSimpleName();

    private Button button;
    private EditText editname;
    private EditText editesatdo;
    private static final int REGISTER_FORM_REQUEST = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editesatdo=(EditText) findViewById(R.id.sviewFindCamionLectur);

    }


    public void findCamionLectura(View view){

        String buscar = editesatdo.getText().toString();

        Camion camion = CamionRepository.buscarCamionbyPlaca(buscar);

        if(camion == null){
            Toast.makeText(AddActivity.this, "Camión no encontrado", Toast.LENGTH_LONG).show();
        }else{
            int idPlaca = camion.getCamion_ID();
            String placa = camion.getPlaca();
            int ejes = camion.getEjes();
            Intent intent = new Intent(AddActivity.this, InfCamionActivity.class);
            intent.putExtra("placa",  placa);
            intent.putExtra("ejes" , ejes);
            intent.putExtra("idPlaca" , idPlaca);
            startActivity(intent);
        }}
    public void callRegister(View view) {


        try {

                Grupoempresa grupoempresa=new Grupoempresa();
                grupoempresa.setNombre(editname.getText().toString());
                grupoempresa.setEstado(editesatdo.getText().toString());

                ApiService service = ApiServiceGenerator.createService(ApiService.class);

                Call call = service.create(grupoempresa);

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.isSuccessful()){
                            Intent intent=new Intent(AddActivity.this,NeumaticoListActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(AddActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
                startActivityForResult(new Intent(this,
                        NeumaticoListActivity.class), REGISTER_FORM_REQUEST);

            }catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();


            }


        }}
