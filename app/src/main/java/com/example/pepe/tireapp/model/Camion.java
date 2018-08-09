package com.example.pepe.tireapp.model;

/**
 * Created by LISET on 06/08/2018.
 */

public class Camion {


    private String fecmod;
    private String usumod;
    private String fecreg;
    private String usureg;
    private String estado;
    private double Kilometraje;
    private double carga_neta;
    private double carga_util;
    private int num_llantas;
    private int ejes;
    private String marca;

    @Override
    public String toString() {
        return "Camion{" +
                "fecmod='" + fecmod + '\'' +
                ", usumod='" + usumod + '\'' +
                ", fecreg='" + fecreg + '\'' +
                ", usureg='" + usureg + '\'' +
                ", estado='" + estado + '\'' +
                ", Kilometraje=" + Kilometraje +
                ", carga_neta=" + carga_neta +
                ", carga_util=" + carga_util +
                ", num_llantas=" + num_llantas +
                ", ejes=" + ejes +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", tag='" + tag + '\'' +
                ", placa='" + placa + '\'' +
                ", empresa_id=" + empresa_id +
                ", camion_ID=" + camion_ID +
                '}';
    }

    private String modelo;
    private String tag;
    private String placa;
    private int empresa_id;
    private int camion_ID;

    public String getFecmod() {
        return fecmod;
    }

    public void setFecmod(String fecmod) {
        this.fecmod = fecmod;
    }

    public String getUsumod() {
        return usumod;
    }

    public void setUsumod(String usumod) {
        this.usumod = usumod;
    }

    public String getFecreg() {
        return fecreg;
    }

    public void setFecreg(String fecreg) {
        this.fecreg = fecreg;
    }

    public String getUsureg() {
        return usureg;
    }

    public void setUsureg(String usureg) {
        this.usureg = usureg;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getKilometraje() {
        return Kilometraje;
    }

    public void setKilometraje(double Kilometraje) {
        this.Kilometraje = Kilometraje;
    }

    public double getCarga_neta() {
        return carga_neta;
    }

    public void setCarga_neta(double carga_neta) {
        this.carga_neta = carga_neta;
    }

    public double getCarga_util() {
        return carga_util;
    }

    public void setCarga_util(double carga_util) {
        this.carga_util = carga_util;
    }

    public int getNum_llantas() {
        return num_llantas;
    }

    public void setNum_llantas(int num_llantas) {
        this.num_llantas = num_llantas;
    }

    public int getEjes() {
        return ejes;
    }

    public void setEjes(int ejes) {
        this.ejes = ejes;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public int getCamion_ID() {
        return camion_ID;
    }

    public void setCamion_ID(int camion_ID) {
        this.camion_ID = camion_ID;
    }
}
