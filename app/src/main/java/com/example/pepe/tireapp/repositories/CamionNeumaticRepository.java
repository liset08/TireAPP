package com.example.pepe.tireapp.repositories;

import com.example.pepe.tireapp.model.Camion_neumaticos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JORDAN on 10/08/2018.
 */

public class CamionNeumaticRepository {

    private static List<Camion_neumaticos> camiones_Neumaticos = new ArrayList<>();


    public static void asignNeumatic(Camion_neumaticos camion_neumaticos){
        camiones_Neumaticos.add(camion_neumaticos);
    }

    public static List<Camion_neumaticos> getList(int id){

        List<Camion_neumaticos> camiones = new ArrayList<>();

        for(int i = 0 ; i < camiones_Neumaticos.size() ; i++){
            if(camiones_Neumaticos.get(i).getCamion_id()==id){
                camiones.add(camiones_Neumaticos.get(i));
            }
        }


        return camiones;
    }

}
