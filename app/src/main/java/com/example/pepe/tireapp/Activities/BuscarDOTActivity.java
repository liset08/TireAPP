package com.example.pepe.tireapp.Activities;

import com.example.pepe.tireapp.MainActivityIngreso;
import com.example.pepe.tireapp.MenuPrincipal;
import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.model.Camion_neumaticos;
import com.example.pepe.tireapp.repositories.CamionNeumaticRepository;
import com.example.pepe.tireapp.repositories.CamionRepository;
import com.example.pepe.tireapp.repositories.NeumaticoRepository;
import com.example.pepe.tireapp.repositories.UsuarioRepository;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarDOTActivity extends AppCompatActivity {

    private static final String TAG = BuscarDOTActivity.class.getSimpleName();

    TextView textViewPlacaPosicion;
    EditText editTextDTO;
    Button btnBuscarDTO;

    String placa;
    String posicion;
    int idPlaca;
    Integer idNeumatico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_dot);

        textViewPlacaPosicion = findViewById(R.id.textViewPlacaPosicion);
        editTextDTO = findViewById(R.id.editTextDTO);
        btnBuscarDTO = findViewById(R.id.btnBuscarDOT);



        placa = getIntent().getExtras().getString("placa");
        posicion = getIntent().getExtras().getString("posicion");
        idPlaca = getIntent().getExtras().getInt("idPlaca");

        textViewPlacaPosicion.setText(placa + " - " + "posicion" + posicion);


        btnBuscarDTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dot = editTextDTO.getText().toString();

                idNeumatico = NeumaticoRepository.buscarNeubyDOT(dot);

                if(idNeumatico==null)
                    Toast.makeText(BuscarDOTActivity.this,"Neumatico no encontrado",Toast.LENGTH_SHORT).show();
                else{
                    new AlertDialog.Builder(BuscarDOTActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_dialer)
                            .setTitle("Neumático encontrado")
                            .setMessage("¿Está seguro que desea asignar el neumático al camión?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    registerNeumaticToTruck();

                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });
    }

    public Camion_neumaticos obtenerValores(){

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime());


        Camion_neumaticos camion_neumaticos = new Camion_neumaticos();
        camion_neumaticos.setCamion_id(idPlaca);
        camion_neumaticos.setNeumatico_id(idNeumatico);
        camion_neumaticos.setPosicion(posicion);
        camion_neumaticos.setUsureg(UsuarioRepository.getUsuario().getUsername());
        camion_neumaticos.setFecreg(timeStamp);
        camion_neumaticos.setEstadouso("1");

        return camion_neumaticos;
    }

    public void registerNeumaticToTruck(){


        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Call call = api.asignarNeumatico(obtenerValores());

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {
                        Toast.makeText(BuscarDOTActivity.this,"REGISTRO EXITOSO " + response.code(), Toast.LENGTH_LONG).show();
                        CamionNeumaticRepository.asignNeumatic(obtenerValores());

                        finish();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(BuscarDOTActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }



            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(BuscarDOTActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
