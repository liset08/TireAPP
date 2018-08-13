package com.example.pepe.tireapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.model.Lectura;
import com.example.pepe.tireapp.repositories.LecturaRepository;

public class AuditoriaEstadoLlantaActivity extends AppCompatActivity {

    int idCamion;
    String posicion, placa;

    EditText editPlaca, editPosicion, editPresion, editDesgaste1, editDesgaste2, editDesgaste3, editObservacion, editAviso;
    Button button, btnCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura);

        inicializar();



        idCamion = getIntent().getExtras().getInt("idPlaca");
        posicion = getIntent().getExtras().getString("posicion");
        placa = getIntent().getExtras().getString("placa");
        Lectura lectura = LecturaRepository.buscarLecturabyCamion(posicion,idCamion);

        editPlaca.setText(placa);

        editPosicion.setText(posicion);

        editPresion.setText(String.valueOf(lectura.getPresion()));
        editObservacion.setText(lectura.getObservacion());
        editDesgaste1.setText(String.valueOf(lectura.getDesgaste_1()));
        editDesgaste2.setText(String.valueOf(lectura.getDesgaste_2()));
        editDesgaste3.setText(String.valueOf(lectura.getDesgaste_3()));

        focusear();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void inicializar(){
        editPlaca = findViewById(R.id.txt_pla_lec);
        editPosicion = findViewById(R.id.txt_pllan_le);
        editPresion = findViewById(R.id.txt_pres_lec);
        editDesgaste1 = findViewById(R.id.txt_des1_lec);
        editDesgaste2 = findViewById(R.id.txt_des2_lec);
        editDesgaste3 = findViewById(R.id.txt_des3_lec);
        editObservacion = findViewById(R.id.txt_obse_le);
        editAviso = findViewById(R.id.txt_taviso_lec);
        button = findViewById(R.id.btn_add_lec);
        btnCancelar = findViewById(R.id.btnCancelarLectura);
        button.setVisibility(View.INVISIBLE);
    }

    private void focusear(){
        editDesgaste1.setFocusable(false);
        editDesgaste2.setFocusable(false);
        editDesgaste3.setFocusable(false);
        editPlaca.setFocusable(false);
        editPosicion.setFocusable(false);
        editPresion.setFocusable(false);
        editObservacion.setFocusable(false);
        editAviso.setFocusable(false);
    }

}
