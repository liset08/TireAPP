package com.example.pepe.tireapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pepe.tireapp.Activities.EditarNeumaticoActivity;
import com.example.pepe.tireapp.Activities.GestionNeumatico;
import com.example.pepe.tireapp.Activities.NeumaticoListActivity;
import com.example.pepe.tireapp.R;
import com.example.pepe.tireapp.ResponseMessage;
import com.example.pepe.tireapp.Service.ApiService;
import com.example.pepe.tireapp.Service.ApiServiceGenerator;
import com.example.pepe.tireapp.model.Grupoempresa;
import com.example.pepe.tireapp.model.TipoNeumatico;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LISET on 24/07/2018.
 */

public class NeumaticoListAdapter extends RecyclerView.Adapter<NeumaticoListAdapter.ViewHolder> {

    private static final String TAG = NeumaticoListAdapter.class.getSimpleName();

    private List<TipoNeumatico> tipoNeumaticos;
    private Activity activity;
    public NeumaticoListAdapter(Activity activity){
        this.tipoNeumaticos = new ArrayList<>();
        this.activity = activity;
    }

    public void setTipoNeumatico(List<TipoNeumatico> tipoNeumaticos) {
        this.tipoNeumaticos = tipoNeumaticos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dotText;
        public TextView marcaText;
        public TextView modeloText;
        public Button btnedit;
        public ImageButton menuButton;

        public ViewHolder(View itemView) {
            super(itemView);
            dotText = itemView.findViewById(R.id.dot_text);
            marcaText = itemView.findViewById(R.id.marca_text);
            modeloText = itemView.findViewById(R.id.modelo_text);
            btnedit = itemView.findViewById(R.id.btn_itemedit);
            menuButton = (ImageButton) itemView.findViewById(R.id.menu_button);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_neumatico, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NeumaticoListAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final TipoNeumatico tipoNeumatico = this.tipoNeumaticos.get(position);

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



        viewHolder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {


                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.remove_button:
try {
    AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
    builder.setTitle("Confirmar");
    builder.setMessage("¿Estas seguro?");
    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ApiService service = ApiServiceGenerator.createService(ApiService.class);

            Call<Void> call = service.delete(tipoNeumatico.getTipo_neumatico_ID());

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()){
                        // Eliminar item del recyclerView y notificar cambios
                        tipoNeumaticos.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, tipoNeumaticos.size());

                        Intent intent = new Intent(activity, NeumaticoListActivity.class);
                        activity.startActivity(intent);


                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });

        }
    });

    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    });
    builder.create().show();
}catch (Exception e){
}
                                break;

                        }
                        return false;
                    }
                });
                popup.show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return this.tipoNeumaticos.size();
    }



}
