package com.example.pepe.tireapp.repositories;

import com.example.pepe.tireapp.model.Lectura;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LISET on 10/08/2018.
 */

public class LecturaRepository {
//usureg=posicion
    private static List<Lectura> lecturas = new ArrayList<>();

    public static void createLectura(Lectura lectura){
        lecturas.add(lectura);
    }

    public static List<Lectura> getList(int id){

        List<Lectura> lec = new ArrayList<>();

        for(int i = 0 ; i < lecturas.size() ; i++){
            if(lecturas.get(i).getCamionID()==id){
                lec.add(lecturas.get(i));
            }
        }


        return lecturas;
    }


    public static Lectura buscarlectura(String posicion){

        Lectura x = null;

        for (int i = 0; i < lecturas.size(); i++){
            if(lecturas.get(i).getUsureg().equals(posicion)){
                x = lecturas.get(i);
            }
        }

        return x;

    }

    public static Lectura buscarLecturabyCamion(String posicion, int idCamion){

        List<Lectura> lecturas = getList(idCamion);
        Lectura lectura = null;

        if(lecturas.isEmpty()){
            return  null;
        }else{
            for(int i = 0; i < lecturas.size();i++){
                if(lecturas.get(i).getUsureg().equals(posicion)){
                    lectura =  lecturas.get(i);
                }
            }
            return lectura;
        }
    }

}
