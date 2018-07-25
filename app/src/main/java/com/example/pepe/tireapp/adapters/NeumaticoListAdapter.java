package com.example.pepe.tireapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.model.Grupoempresa;
import com.example.pepe.tireapp.model.TipoNeumatico;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LISET on 24/07/2018.
 */

public class NeumaticoListAdapter extends RecyclerView.Adapter<NeumaticoListAdapter.ViewHolder> {



    private List<TipoNeumatico> tipoNeumatico;

    public NeumaticoListAdapter(){
        this.tipoNeumatico = new ArrayList<>();
    }

    public void setTipoNeumatico(List<TipoNeumatico> tipoNeumatico) {
        this.tipoNeumatico = tipoNeumatico;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dotText;
        public TextView marcaText;
        public TextView modeloText;


        public ViewHolder(View itemView) {
            super(itemView);
            dotText = itemView.findViewById(R.id.dot_text);
            marcaText = itemView.findViewById(R.id.marca_text);
            modeloText = itemView.findViewById(R.id.modelo_text);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_neumatico, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NeumaticoListAdapter.ViewHolder viewHolder, int position) {
        final TipoNeumatico tipoNeumatico = this.tipoNeumatico.get(position);

        viewHolder.dotText.setText(tipoNeumatico.getDot());
        viewHolder.marcaText.setText(tipoNeumatico.getMarca());
        viewHolder.modeloText.setText(tipoNeumatico.getModelo());


    }

    @Override
    public int getItemCount() {
        return this.tipoNeumatico.size();
    }



}
