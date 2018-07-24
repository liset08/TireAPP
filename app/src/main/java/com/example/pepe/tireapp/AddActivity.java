package com.example.pepe.tireapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.model.Grupoempresa;

import java.util.List;

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

        editesatdo=(EditText) findViewById(R.id.editEstadodi);
        editname=(EditText) findViewById(R.id.editName);
        button=(Button) findViewById(R.id.button);
    }



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
                            Intent intent=new Intent(AddActivity.this,ListaCamionActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(AddActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
                startActivityForResult(new Intent(this,
                        ListaCamionActivity.class), REGISTER_FORM_REQUEST);

            }catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();


            }


        }}
