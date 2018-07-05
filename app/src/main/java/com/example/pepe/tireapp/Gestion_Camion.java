package com.example.pepe.tireapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Gestion_Camion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion__camion);
        Spinner spinner = (Spinner)findViewById(R.id.Modelo);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();

        categories.add("Faw");
        categories.add("Ford");
        categories.add("Foton");
        categories.add("Hyundai");
        categories.add("Isuzu");
        categories.add("Iveco");
        categories.add("Jac");
        categories.add("Jmc");
        categories.add("Mercedes-benz");
        categories.add("Renault");
        categories.add("Scania");
        categories.add("Volkswagen");
        categories.add("Volvo");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    public void Salir (View paramView){
        finish();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
