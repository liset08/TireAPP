package com.example.pepe.tireapp.repositories;

import android.content.Intent;

import com.example.pepe.tireapp.model.TipoNeumatico;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JORDAN on 9/08/2018.
 */

public class NeumaticoRepository {

    public static List<TipoNeumatico> tipoNeumaticos = new ArrayList<>();

    static{
        tipoNeumaticos.add(new TipoNeumatico(1,34,"modelo1","Kidsad","M9 K1M FS"));
        tipoNeumaticos.add(new TipoNeumatico(1,35,"modelo2","Kidsad","M9 K1M F8"));
        tipoNeumaticos.add(new TipoNeumatico(1,36,"modelo3","Continantal","M9 K1M A4"));
        tipoNeumaticos.add(new TipoNeumatico(1,37,"modelo4","Pirelli","M9 K1M B8"));
        tipoNeumaticos.add(new TipoNeumatico(1,38,"modelo5","Dunlop","M9 K1M L2"));
        tipoNeumaticos.add(new TipoNeumatico(1,39,"modelo6","Goodyear","M9 K1M N1"));
    }


    public static  List<TipoNeumatico> getList(){
        return tipoNeumaticos;
    }

    public static void registrarNeumatico(TipoNeumatico tipoNeumatico){
        tipoNeumaticos.add(tipoNeumatico);
    }

    public static Integer buscarNeubyDOT(String dot){

        Integer l = null;

        for (int i = 0 ; i < tipoNeumaticos.size(); i++){
            if(tipoNeumaticos.get(i).getDot().equals(dot)){
                l = tipoNeumaticos.get(i).getNeumatico_id();
            }
        }

        return l;

    }
}
