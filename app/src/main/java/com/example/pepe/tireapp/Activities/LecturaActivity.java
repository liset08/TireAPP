package com.example.pepe.tireapp.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.model.Lectura;
import com.example.pepe.tireapp.repositories.Constante;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LecturaActivity extends AppCompatActivity {

    private Button button;
    private static final String TAG = LecturaActivity.class.getSimpleName();
    private EditText txt_des1_lec,txt_des2_lec,txt_des3_lec,txt_pllan_le,txt_pres_lec,txt_obse_le,txt_taviso_lec,txt_placa_lec;
    private static final int REGISTER_FORM_REQUEST = 100;
    Button btn_add_lec;
    public double presion,d1,d2,d3,promdesgaste;
    public String obser,aviso;
public int idPlaca;
    public String posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura);

        txt_placa_lec=(EditText)findViewById(R.id.txt_pla_lec);
        txt_pllan_le=(EditText) findViewById(R.id.txt_pllan_le);
        btn_add_lec=(Button) findViewById(R.id.btn_add_lec);

        txt_des1_lec=(EditText) findViewById(R.id.txt_des1_lec);
        txt_des2_lec=(EditText) findViewById(R.id.txt_des2_lec);
        txt_des3_lec=(EditText) findViewById(R.id.txt_des3_lec);
        txt_pres_lec=(EditText) findViewById(R.id.txt_pres_lec);
        txt_obse_le=(EditText) findViewById(R.id.txt_obse_le);
        txt_taviso_lec=(EditText) findViewById(R.id.txt_taviso_lec);
        String placa = getIntent().getExtras().getString("placa");
        int ejes = getIntent().getExtras().getInt("ejes");
         idPlaca = getIntent().getExtras().getInt("idPlaca");
        posicion = getIntent().getExtras().getString("posicion");

        txt_placa_lec.setText(placa);
        txt_pllan_le.setText(posicion);

        btn_add_lec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double presion= Double.parseDouble(txt_pres_lec.getText().toString());
                 d1= Double.parseDouble(txt_des1_lec.getText().toString());
                 d2= Double.parseDouble(txt_des2_lec.getText().toString());
                 d3= Double.parseDouble(txt_des3_lec.getText().toString());
                 obser= txt_obse_le.getText().toString();
                promdesgaste=(d1+d2+d3)/3;
                try {



                    if (presion > Constante.presionMaxima) {
                        aviso = "A5";
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Advertencia...!!");
                        builder.setMessage("Presion baja ");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                callRegisterLectura();
//
                            }
                        });
                        builder.create().show();
                    }else if (promdesgaste>Constante.descgaste){
                        aviso = "A4";
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Advertencia...!!");
                        builder.setMessage("Valor de Desgaste superado en le neumatico  ");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                callRegisterLectura();
//
                            }
                        });
                        builder.create().show();

                    }else if (promdesgaste>Constante.descgaste || presion > Constante.presionMaxima){
                        aviso = "A3";
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Advertencia...!!");
                        builder.setMessage("Csmbio de neumatico presion baja y demasiado desgaste");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                callRegisterLectura();
//
                            }
                        });
                        builder.create().show();
                    }else {
                        aviso="";
                        callRegisterLectura();

                    }
                        }catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();


                        }

                    }
                });


    }

    public void callRegisterLectura () {


        final Lectura lectura=new Lectura();

        lectura.setDesgaste_1(d1);
        lectura.setDesgaste_2(d2);
        lectura.setDesgaste_3(d3);
        lectura.setPresion(presion);
        lectura.setObservacion(obser);
        lectura.setCamionID(idPlaca);
        lectura.setNeumaticoID(23);
        lectura.setKilometraje_Neu(13);
        lectura.setEstado(aviso);
        lectura.setVarianza(13.3);

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call call = service.createLectur(lectura);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);


                    if (response.isSuccessful()) {
                        Intent intent = new Intent(LecturaActivity.this, InfCamionActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(LecturaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });





    }

    public void CancelAdd(View paramView){
        Intent intent = new Intent(this, NeumaticoListActivity.class);
        startActivity(intent);

    }



}
