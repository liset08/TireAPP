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

public class MainActivityIngreso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_ingreso);
    }

    public void validarLogin(View paramView) {

        //if (util.checkPlayServices(this)) {

            EditText editTextUsuario = (EditText) findViewById(R.id.editTextUsuario);
            EditText editTextPass = (EditText) findViewById(R.id.editTextPass);
            String pass = editTextPass.getText().toString().trim();
            String usuariodsc = editTextUsuario.getText().toString().trim();

            if(pass.equalsIgnoreCase("liset")&& usuariodsc.equalsIgnoreCase("liset") ){

                Intent intent = new Intent(this, MenuPrincipal.class);
                startActivity(intent);

               // gcm = GoogleCloudMessaging.getInstance(IngresoActivity.this);
                //Obtenemos el Registration ID guardado
          //      boolean ingreso = getRegistrationId(usuariodsc,pass);

            /*    if (!ingreso) {
                    if (new gescom.damo.dyd.tableta.util.Internet(contexto).internet()) {
                        new VerificarUsuario(IngresoActivity.this, usuariodsc.trim(), pass.trim()).execute();
                    } else {
                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(500);
                        Toast.makeText(contexto, "Verifique su conexion a internet", Toast.LENGTH_SHORT).show();
                    }
                }*/
            }else{
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);
                Toast.makeText(MainActivityIngreso.this, "Ingrese su usuario y contraseña", Toast.LENGTH_SHORT).show();
            }
    }


}

