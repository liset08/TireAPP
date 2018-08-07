package com.example.pepe.tireapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.model.Camion;
import com.example.pepe.tireapp.model.Grupoempresa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LISET on 19/07/2018.
 */

public class CamionListAdpater extends RecyclerView.Adapter<CamionListAdpater.ViewHolder> {

    private List<Camion> camiones;
    private Activity activity;
    public CamionListAdpater(Activity activity){
        this.activity = activity;
        this.camiones = new ArrayList<>();
    }
    public void setCamiones(List<Camion> camiones){
        this.camiones = camiones;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView placaCamionText;
        public TextView marcaCamionText;
        public TextView modeloCamionText;
        public Button btneditCamion;
        public ImageView imgCamion;



        public ViewHolder(View itemView) {
            super(itemView);
                placaCamionText = itemView.findViewById(R.id.placaCamionText);
                marcaCamionText = itemView.findViewById(R.id.marcaCamionText);
                modeloCamionText = itemView.findViewById(R.id.modeloCamionText);
                btneditCamion = itemView.findViewById(R.id.btnEditCamion);
                imgCamion = itemView.findViewById(R.id.img_camion);
        }
    }

    @Override
    public CamionListAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camion, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CamionListAdpater.ViewHolder viewHolder, int position) {

       final Camion camion = this.camiones.get(position);

        viewHolder.marcaCamionText.setText(camion.getMarca());
        viewHolder.modeloCamionText.setText(camion.getModelo());
        viewHolder.placaCamionText.setText(camion.getPlaca());

        switch (camion.getEjes()){
            case 2: viewHolder.imgCamion.setImageResource(R.drawable.img_camion_2ej);
                break;
            case 3: viewHolder.imgCamion.setImageResource(R.drawable.img_camion_3ej);
                break;
            case 4: viewHolder.imgCamion.setImageResource(R.drawable.img_camion_4ej);
                break;
            case 5: viewHolder.imgCamion.setImageResource(R.drawable.img_camion_5ej);
                break;
            case 6: viewHolder.imgCamion.setImageResource(R.drawable.img_camion_6ej);
                break;
            case 7: viewHolder.imgCamion.setImageResource(R.drawable.img_camion_7ej);
                break;
            default: viewHolder.imgCamion.setImageResource(R.drawable.img_camion_7ej);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return this.camiones.size();
    }

}

