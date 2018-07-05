package com.example.pepe.tireapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }

    public void GestionCamion(View paramView){

        Intent intent = new Intent(this, Gestion_Camion.class);
        startActivity(intent);

    }

    public void GestionLlanta(View paramView){
        Intent intent = new Intent(this, GestionNeumatico.class);
        startActivity(intent);

    }

    public void Salir(View paramView){

        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
