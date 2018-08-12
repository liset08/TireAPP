package com.example.pepe.tireapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pepe.tireapp.Activities.InfCamionActivity;
import com.example.pepe.tireapp.Activities.LecturaActivity;
import com.example.pepe.tireapp.Activities.NeumaticoListActivity;
import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.model.Camion_neumaticos;
import com.example.pepe.tireapp.model.Lectura;
import com.example.pepe.tireapp.repositories.CamionNeumaticRepository;
import com.example.pepe.tireapp.repositories.LecturaRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambioNeuaticoActivity extends AppCompatActivity {
    public String placa;
    public int ejes, idPlaca;
    private TextView textViewPlaca;
    private static final String TAG = CambioNeuaticoActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_neuatico);

        textViewPlaca = findViewById(R.id.textViewPlacaCambio);
        placa = getIntent().getExtras().getString("placa");
        ejes = getIntent().getExtras().getInt("ejes");
        idPlaca = getIntent().getExtras().getInt("idPlaca");

        textViewPlaca.setText(placa);

        LinearLayout llBotonera1 = (LinearLayout) findViewById(R.id.llBotonera1);
        LinearLayout llBotonera2 = (LinearLayout) findViewById(R.id.llBotonera2);
        LinearLayout llBotonera3 = (LinearLayout) findViewById(R.id.llBotonera3);
        LinearLayout llBotonera4 = (LinearLayout) findViewById(R.id.llBotonera4);
        LinearLayout numeros = (LinearLayout) findViewById(R.id.numeros);
        int numEjes = ejes;
        int buble = numEjes + 1;
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(50,
                100);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(50,
                50);

        int k = 0;
        //Creamos los marcadores que indican la posición de la llanta en el eje Y
        for (int i = 1; i <= buble; i++) {

            //el if cuando i sea 2 solo añadirá un espacio en blanco para diferenciar
            //el primer eje de los demas
            if (i == 2) {
                View view = new View(this);
                view.setLayoutParams(lp2);
                numeros.addView(view);
            } else {
                k = i;
                if (i > 2)
                    k = i - 1;
                TextView text = new TextView(this);
                text.setLayoutParams(lp);
                text.setText(String.valueOf(k));
                text.setTextSize(30);
                text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                numeros.addView(text);
            }
            if (k == numEjes)
                k = 0;
        }

        //Creamos los botones en bucle
        //Posicion A
        for (int i = 1; i <= buble; i++) {

            if (i == 2) {
                View view = new View(this);
                view.setLayoutParams(lp2);
                llBotonera1.addView(view);
            } else {
                k = i;
                if (i > 2)
                    k = i - 1;
                Button button = new Button(this);
                button.setLayoutParams(lp);
                button.setBackgroundResource(R.drawable.neumatico);
                final int s = k;
                final String d = "A"+k;
                final List<Camion_neumaticos> camiones = CamionNeumaticRepository.getList(idPlaca);

                if(this.verificarExistenciaNeumatico(d,camiones)){
                    Camion_neumaticos caneu = CamionNeumaticRepository.buscarneumati(d);
                    final int idneu = caneu.getNeumatico_id();
                    final String id=caneu.getEstadouso();
                    Log.d(TAG, "camion: " + caneu);

                    final Lectura lectura = LecturaRepository.buscarlectura(d);
                    final String codAviso=lectura.getEstado();
                    Log.d(TAG, "camion: " + lectura);


                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                if(codAviso.equalsIgnoreCase("A3")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Advertencia...!!");
                    builder.setIcon(R.drawable.icon_waning);

                    builder.setMessage("Cambio de neumatico presion baja y demasiado desgaste");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ApiService service = ApiServiceGenerator.createService(ApiService.class);

                            Call<Void> call = service.deleteCamNeu(idneu);

                            call.enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    if (response.isSuccessful()){

                                     finish();

                                    }
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {

                                }
                            });

//
                        }
                    });
                    builder.create().show();

}else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Advertencia...!!");
                    builder.setIcon(R.drawable.icon_waning);

                    builder.setMessage("No necesita realizar cambio de neumatico");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


//
                        }
                    });
                    builder.create().show();

                }

                        }
                    });}else{

                }
                llBotonera1.addView(button);
            }
            if (k == numEjes)
                k = 0;
        }

        //Posicion B
        for (int i = 1; i <= buble; i++) {
            if (i == 2) {
                View view = new View(this);
                view.setLayoutParams(lp2);
                llBotonera2.addView(view);
            } else if (i == 1) {
                View view = new View(this);
                view.setLayoutParams(lp);
                llBotonera2.addView(view);
            } else {
                k = i;
                if (i > 2)
                    k = i - 1;
                Button button = new Button(this);
                button.setLayoutParams(lp);
                button.setBackgroundResource(R.drawable.neumatico);

                final int s = k;
                String d = "B"+k;
                List<Camion_neumaticos> camiones = CamionNeumaticRepository.getList(idPlaca);
                if(this.verificarExistenciaNeumatico(d,camiones)){
                    Camion_neumaticos caneu = CamionNeumaticRepository.buscarneumati(d);
                    final int idneu = caneu.getNeumatico_id();

                    final Lectura lectura = LecturaRepository.buscarlectura(d);
                    final String codAviso=lectura.getEstado();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(codAviso.equalsIgnoreCase("A3")){

                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setTitle("Advertencia...!!");
                                builder.setIcon(R.drawable.icon_waning);

                                builder.setMessage("Cambio de neumatico presion baja y demasiado desgaste");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


//
                                    }
                                });
                                builder.create().show();

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setTitle("Advertencia...!!");
                                builder.setIcon(R.drawable.icon_waning);

                                builder.setMessage("No necesita realizar cambio de neumatico");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


//
                                    }
                                });
                                builder.create().show();

                            }

                        }
                    });}else{

                }
                llBotonera2.addView(button);
            }
            if (k == numEjes)
                k = 0;
        }

        //Posicion C
        for (int i = 1; i <= buble; i++) {
            if (i == 2) {
                View view = new View(this);
                view.setLayoutParams(lp2);
                llBotonera3.addView(view);
            } else if (i == 1) {
                View view = new View(this);
                view.setLayoutParams(lp);
                llBotonera3.addView(view);
            } else {
                k = i;
                if (i > 2)
                    k = i - 1;
                Button button = new Button(this);
                button.setLayoutParams(lp);
                button.setBackgroundResource(R.drawable.neumatico);
                final int s = k; String d = "C"+k;
                List<Camion_neumaticos> camiones = CamionNeumaticRepository.getList(idPlaca);
                if(this.verificarExistenciaNeumatico(d,camiones)){
                    Camion_neumaticos caneu = CamionNeumaticRepository.buscarneumati(d);
                    final int idneu = caneu.getNeumatico_id();

                    final Lectura lectura = LecturaRepository.buscarlectura(d);
                    Log.d(TAG, "producto: " + lectura);
                    final String codAviso=lectura.getEstado();


                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(codAviso.equalsIgnoreCase("A3")){

                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setTitle("Advertencia...!!");
                                builder.setIcon(R.drawable.icon_waning);

                                builder.setMessage("Cambio de neumatico presion baja y demasiado desgaste");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


//
                                    }
                                });
                                builder.create().show();

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setTitle("Advertencia...!!");
                                builder.setIcon(R.drawable.icon_waning);

                                builder.setMessage("No necesita realizar cambio de neumatico");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


//
                                    }
                                });
                                builder.create().show();

                            }

                        }
                    });}else{

                }
                llBotonera3.addView(button);
            }
            if (k == numEjes)
                k = 0;
        }

        //Posicion D
        for (int i = 1; i <= buble; i++) {
            if (i == 2) {
                View view = new View(this);
                view.setLayoutParams(lp2);
                llBotonera4.addView(view);
            } else {
                k = i;
                if (i > 2)
                    k = i - 1;
                Button button = new Button(this);
                button.setLayoutParams(lp);
                button.setBackgroundResource(R.drawable.neumatico);
                final int s = k;
                String d = "D"+k;
                List<Camion_neumaticos> camiones = CamionNeumaticRepository.getList(idPlaca);
                if(this.verificarExistenciaNeumatico(d,camiones)){
                    Camion_neumaticos caneu = CamionNeumaticRepository.buscarneumati(d);
                    final int idneu = caneu.getNeumatico_id();

                    final Lectura lectura = LecturaRepository.buscarlectura(d);
                    Log.d(TAG, "producto: " + lectura);
                    final String codAviso=lectura.getEstado();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(codAviso.equalsIgnoreCase("A3")){

                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setTitle("Advertencia...!!");
                                builder.setIcon(R.drawable.icon_waning);

                                builder.setMessage("Cambio de neumatico presion baja y demasiado desgaste");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


//
                                    }
                                });
                                builder.create().show();

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setTitle("Advertencia...!!");
                                builder.setIcon(R.drawable.icon_waning);

                                builder.setMessage("No necesita realizar cambio de neumatico");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


//
                                    }
                                });
                                builder.create().show();

                            }
                        }
                    });
                }else {
                }
                llBotonera4.addView(button);
            }}
        if (k == numEjes)
            k = 0;
    }



    public boolean verificarExistenciaNeumatico(String posicion , List<Camion_neumaticos> camion_neumaticos){
        boolean seraVerdad = false;
        for(int i = 0 ; i < camion_neumaticos.size(); i++){
            if(camion_neumaticos.get(i).getPosicion().equals(posicion))
                seraVerdad = true;
        }

        return seraVerdad;
    }

    public boolean verificarExistenciaLectura(String posicion , List<Lectura> lecturas){
        boolean seraVerdad = false;
        for(int i = 0 ; i < lecturas.size(); i++){
            if(lecturas.get(i).getUsureg().equals(posicion))
                seraVerdad = true;
        }

        return seraVerdad;
    }
}
