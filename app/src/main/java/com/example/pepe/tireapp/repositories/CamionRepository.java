package com.example.pepe.tireapp.repositories;

import com.example.pepe.tireapp.model.Camion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by JORDAN on 9/08/2018.
 */

public class CamionRepository {



    private static List<Camion> camiones = new ArrayList<>();


    static {
        camiones.add(new Camion(1,13,"h1","Subaru", "modelo2","h1",0,6,2,5,4, obtenerFechaActual(),"jrevata","1"));
        camiones.add(new Camion(1,14,"h2","Nissan", "modelo2","h2",0,10,3,5,4,  obtenerFechaActual(),"jrevata","1"));
        camiones.add(new Camion(1,15,"h3","Toyota", "modelo2","h3",0,14,4,5,4,  obtenerFechaActual(),"jrevata","1"));
        camiones.add(new Camion(1,16,"h4","Lamborghini", "modelo2","h4",0,22,6,5,4,  obtenerFechaActual(),"jrevata","1"));

    }

    public static String obtenerFechaActual(){

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }

    public static void añadirCamion(Camion camion){
        camiones.add(camion);
    }

    public static List<Camion> getList(){
        return camiones;
    }


    public static Camion buscarCamionbyPlaca(String placa){

        Camion x = null;

        for (int i = 0; i < camiones.size(); i++){
            if(camiones.get(i).getPlaca().equals(placa)){
                x = camiones.get(i);
            }
        }

        return x;

    }
    
}
