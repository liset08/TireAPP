package com.example.pepe.tireapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pepe.tireapp.Activities.EditarNeumaticoActivity;
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
    private Activity activity;
    public NeumaticoListAdapter(Activity activity){
        this.tipoNeumatico = new ArrayList<>();
        this.activity = activity;
    }

    public void setTipoNeumatico(List<TipoNeumatico> tipoNeumatico) {
        this.tipoNeumatico = tipoNeumatico;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dotText;
        public TextView marcaText;
        public TextView modeloText;
        public Button btnedit;


        public ViewHolder(View itemView) {
            super(itemView);
            dotText = itemView.findViewById(R.id.dot_text);
            marcaText = itemView.findViewById(R.id.marca_text);
            modeloText = itemView.findViewById(R.id.modelo_text);
            btnedit = itemView.findViewById(R.id.btn_itemedit);

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

        viewHolder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditarNeumaticoActivity.class);
                intent.putExtra("tipoNeumatico", tipoNeumatico);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.tipoNeumatico.size();
    }



}
