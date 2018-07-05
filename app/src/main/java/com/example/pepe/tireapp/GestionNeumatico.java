package com.example.pepe.tireapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class GestionNeumatico extends AppCompatActivity   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_neumatico);
    }

    public void PruebaEquipo(View paramView) {


        Toast toast1 = Toast.makeText(getApplicationContext(),"Toast por defecto", Toast.LENGTH_SHORT);

        toast1.show();
    }

    private void doInventory() {


    }
}
