package com.example.pepe.tireapp.repositories;

import com.example.pepe.tireapp.model.Usuario;
import com.orm.SugarContext;
import com.orm.SugarRecord;

/**
 * Created by JORDAN on 8/08/2018.
 */

public class UsuarioRepository {


    public static void create(Usuario user){
        SugarRecord.save(user);

    }
    public static Usuario getUsuario(){
        Usuario usuario = SugarRecord.listAll(Usuario.class).get(0);
        return usuario;
    }

    public static boolean verifyLogeo(){
        boolean user = SugarRecord.listAll(Usuario.class).isEmpty();

        return user;
    }

    public  static void logout(){
        SugarRecord.deleteAll(Usuario.class);
    }

}
