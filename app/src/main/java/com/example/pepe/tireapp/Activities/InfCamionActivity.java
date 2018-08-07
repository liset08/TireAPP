package com.example.pepe.tireapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.model.Camion;
import com.example.pepe.tireapp.model.TipoNeumatico;

import org.w3c.dom.Text;

public class InfCamionActivity extends AppCompatActivity {

    private TextView textViewPlaca;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_camion);

        textViewPlaca = findViewById(R.id.textViewPlaca);

        String placa = getIntent().getExtras().getString("placa");
        int ejes = getIntent().getExtras().getInt("ejes");
        textViewPlaca.setText(placa);

        LinearLayout llBotonera1 = (LinearLayout) findViewById(R.id.llBotonera1);
        LinearLayout llBotonera2 = (LinearLayout) findViewById(R.id.llBotonera2);
        LinearLayout llBotonera3 = (LinearLayout) findViewById(R.id.llBotonera3);
        LinearLayout llBotonera4 = (LinearLayout) findViewById(R.id.llBotonera4);
        LinearLayout numeros = (LinearLayout) findViewById(R.id.numeros);
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
                button.setBackgroundResource(R.drawable.neumatico);
                final int s = k;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(InfCamionActivity.this,"A" + String.valueOf(s),Toast.LENGTH_SHORT).show();
                    }
                });
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
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(InfCamionActivity.this,"B" + String.valueOf(s),Toast.LENGTH_SHORT).show();
                    }
                });
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
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(InfCamionActivity.this,"C" + String.valueOf(s),Toast.LENGTH_SHORT).show();
                    }
                });
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
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(InfCamionActivity.this,"D" + String.valueOf(s),Toast.LENGTH_SHORT).show();
                    }
                });
                llBotonera4.addView(button);
            }
            if(k == numEjes)
                k=0;
        }


    }
}
