package com.example.pepe.tireapp;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.model.Login;
import com.example.pepe.tireapp.model.Usuario;
import com.example.pepe.tireapp.repositories.UsuarioRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityIngreso extends AppCompatActivity {

    private static final String TAG = MainActivityIngreso.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_ingreso);
    }

    public void validarLogin(View paramView) {

        EditText editTextUsuario = (EditText) findViewById(R.id.editTextUsuario);
        EditText editTextPass = (EditText) findViewById(R.id.editTextPass);
        String pass = editTextPass.getText().toString();
        String usuariodsc = editTextUsuario.getText().toString();

        Login login = new Login();
        login.setUsername(usuariodsc);
        login.setPass(pass);

            if(pass.equals("") || usuariodsc.equals(""))
                Toast.makeText(this,"Por favor rellene los campos requeridos",Toast.LENGTH_SHORT).show();
            else{

                ApiService api = ApiServiceGenerator.createService(ApiService.class);

                Call<List<Usuario>> call = api.login(login);

                call.enqueue(new Callback<List<Usuario>>() {
                    @Override
                    public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                        try{
                            int statusCode = response.code();
                            Log.d(TAG, "HTTP status code: " + statusCode);

                            if(response.isSuccessful()){
                                Usuario user = response.body().get(0);
                                //Agrego los datos a la cache del celular con SUGAR ORM
                                UsuarioRepository.create(user);
                                Toast.makeText(MainActivityIngreso.this, "Bienvenido " + user.getNombre(),Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivityIngreso.this, MenuPrincipal.class);
                                startActivity(intent);
                                MainActivityIngreso.this.finish();

                            }else{
                                Log.e(TAG, "onError: " + response.errorBody().string());
                                throw new Exception("Error en el servicio");
                            }
                        }catch (Throwable t){
                            try {
                                Log.e(TAG, "onThrowable: " + t.toString(), t);
                                Toast.makeText(MainActivityIngreso.this, "Usuario y/o contraseña incorrecta", Toast.LENGTH_LONG).show();
                            }catch (Throwable x){}
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Usuario>> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.toString());
                        Toast.makeText(MainActivityIngreso.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }

    }


}

