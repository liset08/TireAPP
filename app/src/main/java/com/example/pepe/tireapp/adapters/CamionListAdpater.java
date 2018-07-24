package com.example.pepe.tireapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.model.Grupoempresa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LISET on 19/07/2018.
 */

public class CamionListAdpater extends RecyclerView.Adapter<CamionListAdpater.ViewHolder> {

    private List<Grupoempresa> grupoempresa;

    public CamionListAdpater(){
        this.grupoempresa = new ArrayList<>();
    }
    public void setGrupoempresa(List<Grupoempresa> grupoempresa){
        this.grupoempresa = grupoempresa;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{



        public TextView nombreText;
        public TextView precioText;

        public ViewHolder(View itemView) {
            super(itemView);
            nombreText = itemView.findViewById(R.id.namempresa_text);
            precioText = itemView.findViewById(R.id.estado_text);
        }
    }

    @Override
    public CamionListAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camion, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CamionListAdpater.ViewHolder viewHolder, int position) {

       final Grupoempresa grupoempresa = this.grupoempresa.get(position);

        viewHolder.nombreText.setText(grupoempresa.getNombre());
        viewHolder.precioText.setText(grupoempresa.getEstado());



    }

    @Override
    public int getItemCount() {
        return this.grupoempresa.size();
    }

}

