
package com.example.pepe.tireapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pepe.tireapp.CambioNeuaticoActivity;
import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.model.Camion;
import com.example.pepe.tireapp.model.Camion_neumaticos;
import com.example.pepe.tireapp.model.TipoNeumatico;
import com.example.pepe.tireapp.repositories.CamionNeumaticRepository;
import com.example.pepe.tireapp.repositories.CamionRepository;

import org.w3c.dom.Text;

import java.util.List;

public class InfCamionActivity extends AppCompatActivity {
    public String placa;
    public int ejes, idPlaca;
    private TextView textViewPlaca;

    private static final String TAG = InfCamionActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_camion);



        textViewPlaca = findViewById(R.id.textViewPlaca);
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
                String d = "A"+k;
                final List<Camion_neumaticos> camiones = CamionNeumaticRepository.getList(idPlaca);
                if(this.verificarExistenciaNeumatico(d,camiones)){
                    Camion_neumaticos caneu = CamionNeumaticRepository.buscarneumati(d);
                    final int idneu = caneu.getNeumatico_id();
                    Log.d(TAG, "producto: " + idneu);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(InfCamionActivity.this, "A" + String.valueOf(s), Toast.LENGTH_SHORT).show();
                        String pos = "A" + String.valueOf(s);
                        Intent intent = new Intent(InfCamionActivity.this, LecturaActivity.class);
                        intent.putExtra("placa", placa);
                        intent.putExtra("idPlaca", idPlaca);
                        intent.putExtra("posicion", pos);
                        intent.putExtra("ejes", ejes);
                        intent.putExtra("idneu", idneu);


                        startActivity(intent);
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

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(InfCamionActivity.this, "B" + String.valueOf(s), Toast.LENGTH_SHORT).show();
                        String pos = "B" + String.valueOf(s);
                        Intent intent = new Intent(InfCamionActivity.this, LecturaActivity.class);
                        intent.putExtra("placa", placa);
                        intent.putExtra("idPlaca", idPlaca);
                        intent.putExtra("posicion", pos);
                        intent.putExtra("ejes", ejes);
                        intent.putExtra("idneu", idneu);
                        startActivity(intent);

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
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(InfCamionActivity.this, "C" + String.valueOf(s), Toast.LENGTH_SHORT).show();
                        String pos = "C" + String.valueOf(s);
                        Intent intent = new Intent(InfCamionActivity.this, LecturaActivity.class);
                        intent.putExtra("placa", placa);
                        intent.putExtra("idPlaca", idPlaca);
                        intent.putExtra("posicion", pos);
                        intent.putExtra("ejes", ejes);
                        intent.putExtra("idneu", idneu);

                        startActivity(intent);

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
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(InfCamionActivity.this, "D" + String.valueOf(s), Toast.LENGTH_SHORT).show();
                        String pos = "D" + String.valueOf(s);
                        Intent intent = new Intent(InfCamionActivity.this, LecturaActivity.class);
                        intent.putExtra("placa", placa);
                        intent.putExtra("idPlaca", idPlaca);
                        intent.putExtra("posicion", pos);
                        intent.putExtra("ejes", ejes);
                        intent.putExtra("idneu", idneu);

                        startActivity(intent);

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
    }

