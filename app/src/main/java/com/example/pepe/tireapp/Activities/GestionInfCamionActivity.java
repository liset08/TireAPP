package com.example.pepe.tireapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.model.Camion_neumaticos;
import com.example.pepe.tireapp.repositories.CamionNeumaticRepository;

import java.util.List;

public class GestionInfCamionActivity extends AppCompatActivity {



    private static final int REGISTER_FORM_REQUEST = 100;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REGISTER_FORM_REQUEST) {
            //Refresh del intent
            initialize();
        }
    }

    LinearLayout llBotonera1;
    LinearLayout llBotonera2;
    LinearLayout llBotonera3;
    LinearLayout llBotonera4;
    LinearLayout numeros;

    private TextView textViewPlaca;

    String placa;
    int ejes;
    int idPlaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_camion);

        textViewPlaca = findViewById(R.id.textViewPlaca);

        placa = getIntent().getExtras().getString("placa");
        ejes = getIntent().getExtras().getInt("ejes");
        idPlaca = getIntent().getExtras().getInt("idPlaca");


        textViewPlaca.setText(placa);

        initialize();



    }

    public void removeAllViews(){
        llBotonera1.removeAllViews();
        llBotonera2.removeAllViews();
        llBotonera3.removeAllViews();
        llBotonera4.removeAllViews();
        numeros.removeAllViews();
    }

    public void initialize(){
        llBotonera1 = (LinearLayout) findViewById(R.id.llBotonera1);
        llBotonera2 = (LinearLayout) findViewById(R.id.llBotonera2);
        llBotonera3 = (LinearLayout) findViewById(R.id.llBotonera3);
        llBotonera4 = (LinearLayout) findViewById(R.id.llBotonera4);
        numeros = (LinearLayout) findViewById(R.id.numeros);
        int numEjes = ejes;
        int buble = numEjes+1;
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(50,
                100 );
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(50,
                50 );

        int k=0;
        //Creamos los marcadores que indican la posición de la llanta en el eje Y
        for (int i = 1; i <= buble; i++) {

            //el if cuando i sea 2 solo añadirá un espacio en blanco para diferenciar
            //el primer eje de los demas
            if(i == 2){
                View view = new View(this);
                view.setLayoutParams(lp2);
                numeros.addView(view);
            }else{
                k=i;
                if(i>2)
                    k=i-1;
                TextView text = new TextView(this);
                text.setLayoutParams(lp);
                text.setText(String.valueOf(k));
                text.setTextSize(30);
                text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                numeros.addView(text);
            }
            if(k == numEjes)
                k=0;
        }

        //Creamos los botones en bucle
        //Posicion A
        for (int i = 1; i <= buble; i++) {

            if(i == 2){
                View view = new View(this);
                view.setLayoutParams(lp2);
                llBotonera1.addView(view);
            }else{
                k=i;
                if(i>2)
                    k=i-1;
                Button button = new Button(this);
                button.setLayoutParams(lp);
                final int s = k;
                String d = "A"+k;
                List<Camion_neumaticos> camiones = CamionNeumaticRepository.getList(idPlaca);
                if(camiones.isEmpty()){
                    button.setBackgroundResource(R.drawable.neumatico);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(GestionInfCamionActivity.this,"A" + String.valueOf(s),Toast.LENGTH_SHORT).show();
                            String pos="A"+String.valueOf(s);
                            Intent intent = new Intent(GestionInfCamionActivity.this, BuscarDOTActivity.class);
                            intent.putExtra("placa",  placa);
                            intent.putExtra("idPlaca" , idPlaca);
                            intent.putExtra("posicion" , pos);
                            removeAllViews();
                            startActivityForResult(intent, REGISTER_FORM_REQUEST);

                        }
                    });
                }else{
                    if(this.verificarExistenciaNeumatico(d,camiones)){
                        button.setBackgroundResource(R.drawable.neumaticogood);
                    }else{
                        button.setBackgroundResource(R.drawable.neumatico);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(GestionInfCamionActivity.this,"A" + String.valueOf(s),Toast.LENGTH_SHORT).show();
                                String pos="A"+String.valueOf(s);
                                Intent intent = new Intent(GestionInfCamionActivity.this, BuscarDOTActivity.class);
                                intent.putExtra("placa",  placa);
                                intent.putExtra("idPlaca" , idPlaca);
                                intent.putExtra("posicion" , pos);
                                removeAllViews();
                                startActivityForResult(intent, REGISTER_FORM_REQUEST);

                            }
                        });
                    }
                }
                llBotonera1.addView(button);
            }
            if(k == numEjes)
                k=0;
        }

        //Posicion B
        for (int i = 1; i <= buble; i++) {
            if(i == 2){
                View view = new View(this);
                view.setLayoutParams(lp2);
                llBotonera2.addView(view);
            }
            else if(i == 1){
                View view = new View(this);
                view.setLayoutParams(lp);
                llBotonera2.addView(view);
            }
            else{
                k=i;
                if(i>2)
                    k=i-1;
                Button button = new Button(this);
                button.setLayoutParams(lp);
                button.setBackgroundResource(R.drawable.neumatico);
                final int s = k;
                String d = "B"+k;
                List<Camion_neumaticos> camiones = CamionNeumaticRepository.getList(idPlaca);
                if(camiones.isEmpty()){
                    button.setBackgroundResource(R.drawable.neumatico);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(GestionInfCamionActivity.this,"B" + String.valueOf(s),Toast.LENGTH_SHORT).show();
                            String pos="B"+String.valueOf(s);
                            Intent intent = new Intent(GestionInfCamionActivity.this, BuscarDOTActivity.class);
                            intent.putExtra("placa",  placa);
                            intent.putExtra("idPlaca" , idPlaca);
                            intent.putExtra("posicion" , pos);
                            removeAllViews();
                            startActivityForResult(intent, REGISTER_FORM_REQUEST);

                        }
                    });
                }else{
                    if(this.verificarExistenciaNeumatico(d,camiones)){
                        button.setBackgroundResource(R.drawable.neumaticogood);
                    }else{
                        button.setBackgroundResource(R.drawable.neumatico);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(GestionInfCamionActivity.this,"B" + String.valueOf(s),Toast.LENGTH_SHORT).show();
                                String pos="B"+String.valueOf(s);
                                Intent intent = new Intent(GestionInfCamionActivity.this, BuscarDOTActivity.class);
                                intent.putExtra("placa",  placa);
                                intent.putExtra("idPlaca" , idPlaca);
                                intent.putExtra("posicion" , pos);
                                removeAllViews();
                                startActivityForResult(intent, REGISTER_FORM_REQUEST);

                            }
                        });
                    }
                }
                llBotonera2.addView(button);
            }
            if(k == numEjes)
                k=0;
        }

        //Posicion C
        for (int i = 1; i <= buble; i++) {
            if(i == 2){
                View view = new View(this);
                view.setLayoutParams(lp2);
                llBotonera3.addView(view);
            }
            else if(i == 1){
                View view = new View(this);
                view.setLayoutParams(lp);
                llBotonera3.addView(view);
            }
            else {
                k = i;
                if (i > 2)
                    k = i - 1;
                Button button = new Button(this);
                button.setLayoutParams(lp);
                button.setBackgroundResource(R.drawable.neumatico);
                final int s = k;
                String d = "C"+k;
                List<Camion_neumaticos> camiones = CamionNeumaticRepository.getList(idPlaca);
                if(camiones.isEmpty()){
                    button.setBackgroundResource(R.drawable.neumatico);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(GestionInfCamionActivity.this,"C" + String.valueOf(s),Toast.LENGTH_SHORT).show();
                            String pos="C"+String.valueOf(s);
                            Intent intent = new Intent(GestionInfCamionActivity.this, BuscarDOTActivity.class);
                            intent.putExtra("placa",  placa);
                            intent.putExtra("idPlaca" , idPlaca);
                            intent.putExtra("posicion" , pos);
                            removeAllViews();
                            startActivityForResult(intent, REGISTER_FORM_REQUEST);

                        }
                    });
                }else{
                    if(this.verificarExistenciaNeumatico(d,camiones)){
                        button.setBackgroundResource(R.drawable.neumaticogood);
                    }else{
                        button.setBackgroundResource(R.drawable.neumatico);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(GestionInfCamionActivity.this,"C" + String.valueOf(s),Toast.LENGTH_SHORT).show();
                                String pos="C"+String.valueOf(s);
                                Intent intent = new Intent(GestionInfCamionActivity.this, BuscarDOTActivity.class);
                                intent.putExtra("placa",  placa);
                                intent.putExtra("idPlaca" , idPlaca);
                                intent.putExtra("posicion" , pos);
                                removeAllViews();
                                startActivityForResult(intent, REGISTER_FORM_REQUEST);

                            }
                        });
                    }
                }
                llBotonera3.addView(button);
            }
            if(k == numEjes)
                k=0;
        }

        //Posicion D
        for (int i = 1; i <= buble; i++) {
            if(i == 2){
                View view = new View(this);
                view.setLayoutParams(lp2);
                llBotonera4.addView(view);
            }else {
                k = i;
                if (i > 2)
                    k = i - 1;
                Button button = new Button(this);
                button.setLayoutParams(lp);
                button.setBackgroundResource(R.drawable.neumatico);
                final int s = k;
                String d = "D"+k;
                List<Camion_neumaticos> camiones = CamionNeumaticRepository.getList(idPlaca);
                if(camiones.isEmpty()){
                    button.setBackgroundResource(R.drawable.neumatico);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(GestionInfCamionActivity.this,"D" + String.valueOf(s),Toast.LENGTH_SHORT).show();
                            String pos="D"+String.valueOf(s);
                            Intent intent = new Intent(GestionInfCamionActivity.this, BuscarDOTActivity.class);
                            intent.putExtra("placa",  placa);
                            intent.putExtra("idPlaca" , idPlaca);
                            intent.putExtra("posicion" , pos);
                            removeAllViews();
                            startActivityForResult(intent, REGISTER_FORM_REQUEST);

                        }
                    });
                }else{
                    if(this.verificarExistenciaNeumatico(d,camiones)){
                        button.setBackgroundResource(R.drawable.neumaticogood);
                    }else{
                        button.setBackgroundResource(R.drawable.neumatico);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(GestionInfCamionActivity.this,"D" + String.valueOf(s),Toast.LENGTH_SHORT).show();
                                String pos="D"+String.valueOf(s);
                                Intent intent = new Intent(GestionInfCamionActivity.this, BuscarDOTActivity.class);
                                intent.putExtra("placa",  placa);
                                intent.putExtra("idPlaca" , idPlaca);
                                intent.putExtra("posicion" , pos);
                                removeAllViews();
                                startActivityForResult(intent, REGISTER_FORM_REQUEST);

                            }
                        });
                    }
                }
                llBotonera4.addView(button);
            }
            if(k == numEjes)
                k=0;
        }
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
