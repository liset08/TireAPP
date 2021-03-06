package com.example.pepe.tireapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pepe.tireapp.Gestion_Camion;
import com.example.pepe.tireapp.MenuPrincipal;
import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.model.Camion;
import com.example.pepe.tireapp.model.Grupoempresa;
import com.example.pepe.tireapp.model.TipoNeumatico;
import com.example.pepe.tireapp.repositories.CamionRepository;
import com.example.pepe.tireapp.repositories.NeumaticoRepository;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GestionNeumatico extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {


    private Button button;
    private String pin,pin2;
    private EditText txt_dot,txt_marca,txt_modelo,txt_ancho,txt_radial,txt_carga,txt_alt,txt_tempera,txt_pmax,txt_pmin,txt_precio;
    private static final int REGISTER_FORM_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_neumatico);
        button=(Button) findViewById(R.id.btn_addneumatico);


        declararvar();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(datosNuema()== null){
                    Toast.makeText(GestionNeumatico.this,"Complete todos los campos", Toast.LENGTH_LONG).show();
                }else{
                    final TipoNeumatico tipoNeumatico = datosNuema();
                    ApiService service = ApiServiceGenerator.createService(ApiService.class);

                    Call call = service.createNeumatico(tipoNeumatico);

                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Toast.makeText(GestionNeumatico.this,"REGISTRO EXITOSO", Toast.LENGTH_LONG).show();
                            finish();
                            NeumaticoRepository.registrarNeumatico(tipoNeumatico);
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(GestionNeumatico.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
    }

    public void declararvar(){
        txt_dot=(EditText) findViewById(R.id.txtdot);
        txt_ancho=(EditText) findViewById(R.id.txt_ancho);
        txt_radial=(EditText) findViewById(R.id.txt_radial);
        txt_carga=(EditText) findViewById(R.id.txt_carga);
        txt_alt=(EditText) findViewById(R.id.txtAltura);
        txt_tempera=(EditText) findViewById(R.id.txt_temper);
        txt_pmax=(EditText) findViewById(R.id.txtpresionma);
        txt_pmin=(EditText) findViewById(R.id.txtpresionmi);
        txt_precio=(EditText) findViewById(R.id.txt_precio);


        Spinner spinner = (Spinner) findViewById(R.id.marcaSpinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.modeloSpinner);

        String[] letra = {"Marca","Dunlop","Michelin","Hankook","Pirelli","Bridgestone","Goodyear"};
        String[] letra2 = {"Modelo","Dunlop","Michelin","Hankook","Pirelli","Bridgestone","Goodyear"};

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));
        spinner2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra2));
        ArrayList<TipoNeumatico> emp= new ArrayList<TipoNeumatico>();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {

                pin= (String) adapterView.getItemAtPosition(pos);
                Toast.makeText(GestionNeumatico.this, pin, Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {

                pin2= (String) adapterView.getItemAtPosition(pos);
                Toast.makeText(GestionNeumatico.this, pin, Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });



    }
    private TipoNeumatico datosNuema(){

        if(     txt_dot.getText().toString().isEmpty() || txt_ancho.getText().toString().isEmpty() ||
                txt_radial.getText().toString().isEmpty() || txt_carga.getText().toString().isEmpty() ||
                txt_alt.getText().toString().isEmpty() || txt_tempera.getText().toString().isEmpty() ||
                txt_pmax.getText().toString().isEmpty() || txt_pmin.getText().toString().isEmpty() ||
                txt_precio.getText().toString().isEmpty()){

            return null;
        }else{
        TipoNeumatico tipoNeumatico=new TipoNeumatico();

        tipoNeumatico.setDot(txt_dot.getText().toString());
        tipoNeumatico.setAncho(Integer.parseInt(txt_ancho.getText().toString()));
        tipoNeumatico.setRadial(txt_radial.getText().toString());
        tipoNeumatico.setIndice_carga(txt_carga.getText().toString());
        tipoNeumatico.setAltura_max(Double.parseDouble(txt_alt.getText().toString()));
        tipoNeumatico.setTemperatura(Double.parseDouble(txt_tempera.getText().toString()));
        tipoNeumatico.setPresion_max(Double.parseDouble(txt_pmax.getText().toString()));
        tipoNeumatico.setPresion_min(Double.parseDouble(txt_pmin.getText().toString()));
        tipoNeumatico.setPrecio(Double.parseDouble(txt_precio.getText().toString()));
        tipoNeumatico.setMarca(pin);
        tipoNeumatico.setModelo(pin2);

        return tipoNeumatico;
        }
    }
  /*  public void callRegisterNeumatico (View view) {


        try {

            TipoNeumatico tipoNeumatico=new TipoNeumatico();

            tipoNeumatico.setDot(txt_dot.getText().toString());
            tipoNeumatico.setAncho(Integer.parseInt(txt_ancho.getText().toString()));
            tipoNeumatico.setRadial(txt_radial.getText().toString());
            tipoNeumatico.setIndice_carga(txt_carga.getText().toString());
            tipoNeumatico.setAltura_max(Double.parseDouble(txt_alt.getText().toString()));
            tipoNeumatico.setTemperatura(Double.parseDouble(txt_tempera.getText().toString()));
            tipoNeumatico.setPresion_max(Double.parseDouble(txt_pmax.getText().toString()));
            tipoNeumatico.setPresion_min(Double.parseDouble(txt_pmin.getText().toString()));
            tipoNeumatico.setPrecio(Double.parseDouble(txt_precio.getText().toString()));
            tipoNeumatico.setMarca(pin);
            tipoNeumatico.setModelo(pin2);



            ApiService service = ApiServiceGenerator.createService(ApiService.class);

            Call call = service.createNeumatico(tipoNeumatico);

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()){
                        Intent intent=new Intent(GestionNeumatico.this,NeumaticoListActivity.class);


                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(GestionNeumatico.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
            startActivityForResult(new Intent(this,
                    NeumaticoListActivity.class), REGISTER_FORM_REQUEST);

        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();


        }


    }
*/

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public void CancelAdd(View paramView){
        finish();

    }
   }