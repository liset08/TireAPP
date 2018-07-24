package com.example.pepe.tireapp.model;

import java.util.List;

/**
 * Created by LISET on 18/07/2018.
 */

public class Grupoempresa {

    @Override
    public String toString() {
        return "Grupoempresa{" +
                "estado='" + estado + '\'' +
                ", nombre='" + nombre + '\'' +
                ", GrupoEmpresa_ID=" + GrupoEmpresa_ID +
                '}';
    }

    private String estado;
    private String nombre;
    private int GrupoEmpresa_ID;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getGrupoEmpresa_ID() {
        return GrupoEmpresa_ID;
    }

    public void setGrupoEmpresa_ID(int GrupoEmpresa_ID) {
        this.GrupoEmpresa_ID = GrupoEmpresa_ID;
    }


}
