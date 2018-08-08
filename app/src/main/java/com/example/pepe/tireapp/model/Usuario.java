package com.example.pepe.tireapp.model;

import com.orm.dsl.Table;

/**
 * Created by JORDAN on 8/08/2018.
 */

@Table
public class Usuario {
    public Integer usuarioID;
    public Integer grupoEmpresa_ID;
    public String username;
    public String password;
    public String nombre;
    public String apellido;
    public String rol;
    public String empresa;

    public Integer getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Integer usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Integer getGrupoEmpresa_ID() {
        return grupoEmpresa_ID;
    }

    public void setGrupoEmpresa_ID(Integer grupoEmpresa_ID) {
        this.grupoEmpresa_ID = grupoEmpresa_ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
