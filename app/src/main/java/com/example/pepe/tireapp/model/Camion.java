package com.example.pepe.tireapp.model;

/**
 * Created by JORDAN on 1/08/2018.
 */

public class Camion {

    private Integer camionID;
    private Integer empresaId;
    private String placa;
    private String tag;
    private String modelo;
    private String marca;
    private Integer ejes;
    private Integer num_llantas;
    private Double carga_util;
    private Double carga_neta;
    private Double kilometraje;
    private String estado;
    private String usureg;
    private String fecreg;
    private String usumod;
    private String fecmod;

    public Integer getCamionID() {
        return camionID;
    }

    public void setCamionID(Integer camionID) {
        this.camionID = camionID;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getEjes() {
        return ejes;
    }

    public void setEjes(Integer ejes) {
        this.ejes = ejes;
    }

    public Integer getNum_llantas() {
        return num_llantas;
    }

    public void setNum_llantas(Integer num_llantas) {
        this.num_llantas = num_llantas;
    }

    public Double getCarga_util() {
        return carga_util;
    }

    public void setCarga_util(Double carga_util) {
        this.carga_util = carga_util;
    }

    public Double getCarga_neta() {
        return carga_neta;
    }

    public void setCarga_neta(Double carga_neta) {
        this.carga_neta = carga_neta;
    }

    public Double getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsureg() {
        return usureg;
    }

    public void setUsureg(String usureg) {
        this.usureg = usureg;
    }

    public String getFecreg() {
        return fecreg;
    }

    public void setFecreg(String fecreg) {
        this.fecreg = fecreg;
    }

    public String getUsumod() {
        return usumod;
    }

    public void setUsumod(String usumod) {
        this.usumod = usumod;
    }

    public String getFecmod() {
        return fecmod;
    }

    public void setFecmod(String fecmod) {
        this.fecmod = fecmod;
    }
}
