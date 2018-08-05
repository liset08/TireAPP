package com.example.pepe.tireapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.model.TipoNeumatico;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarNeumaticoActivity extends AppCompatActivity {
    private Button buttonedit;
    private String pin,pin2;
    private EditText txt_dot,txt_marca,txt_modelo,txt_ancho,txt_radial,txt_carga,txt_alt,txt_tempera,txt_pmax,txt_pmin,txt_precio;
    private static final int REGISTER_FORM_REQUEST = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_neumatico);



        txt_dot=(EditText) findViewById(R.id.txtdot_e);
        txt_ancho=(EditText) findViewById(R.id.txt_ancho_e);
        txt_radial=(EditText) findViewById(R.id.txt_radial_e);
        txt_carga=(EditText) findViewById(R.id.txt_carga_e);
        txt_alt=(EditText) findViewById(R.id.txtAltura_e);
        txt_tempera=(EditText) findViewById(R.id.txt_temper_e);
        txt_pmax=(EditText) findViewById(R.id.txtpresionma_e);
        txt_pmin=(EditText) findViewById(R.id.txtpresionmi_e);
        txt_precio=(EditText) findViewById(R.id.txt_precio_e);
        buttonedit=(Button) findViewById(R.id.btn_editneumatico);



        Spinner spinner = (Spinner) findViewById(R.id.marcaSpinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.modeloSpinner);

        Bundle intent=getIntent().getExtras();
        final TipoNeumatico tipoNeumatico= intent.getParcelable("tipoNeumatico");

        txt_dot.setText(String.valueOf(tipoNeumatico.getDot()));
        txt_ancho.setText(String.valueOf(tipoNeumatico.getAncho()));
        txt_radial.setText(String.valueOf(tipoNeumatico.getRadial()));
        txt_carga.setText(String.valueOf(tipoNeumatico.getIndice_carga()));
        txt_alt.setText(String.valueOf(tipoNeumatico.getAltura_max()));
        txt_tempera.setText(String.valueOf(tipoNeumatico.getTemperatura()));
        txt_pmax.setText(String.valueOf(tipoNeumatico.getPresion_max()));
        txt_pmin.setText(String.valueOf(tipoNeumatico.getPresion_min()));
        txt_precio.setText(String.valueOf(tipoNeumatico.getPrecio()));



        buttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    tipoNeumatico.setDot(txt_dot.getText().toString());
                    tipoNeumatico.setAncho(Integer.parseInt(txt_ancho.getText().toString()));
                    tipoNeumatico.setRadial(txt_radial.getText().toString());
                    tipoNeumatico.setIndice_carga(txt_carga.getText().toString());
                    tipoNeumatico.setAltura_max(Double.parseDouble(txt_alt.getText().toString()));
                    tipoNeumatico.setTemperatura(Double.parseDouble(txt_tempera.getText().toString()));
                    tipoNeumatico.setPresion_max(Double.parseDouble(txt_pmax.getText().toString()));
                    tipoNeumatico.setPresion_min(Double.parseDouble(txt_pmin.getText().toString()));
                    tipoNeumatico.setPrecio(Double.parseDouble(txt_precio.getText().toString()));

                    ApiService service = ApiServiceGenerator.createService(ApiService.class);

                    Call call = service.update(tipoNeumatico);

                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.isSuccessful()){
                                Intent intent=new Intent(EditarNeumaticoActivity.this,NeumaticoListActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(EditarNeumaticoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });

                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();


                }
            }
        });


    }
    public void CancelEdit(View paramView){
        Intent intent = new Intent(this, NeumaticoListActivity.class);
        startActivity(intent);

    }
   }



