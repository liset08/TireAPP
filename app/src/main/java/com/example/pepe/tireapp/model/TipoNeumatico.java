package com.example.pepe.tireapp.model;

import android.os.Parcel;
import android.os.Parcelable;



/**
 * Created by LISET on 23/07/2018.
 */

public class TipoNeumatico implements Parcelable {

    public TipoNeumatico() {

    }

    public String getDot() {
        return dot;
    }

    public void setDot(String dot) {
        this.dot = dot;
    }

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

    public double getPresion_max() {
        return presion_max;
    }

    public void setPresion_max(double presion_max) {
        this.presion_max = presion_max;
    }

    public double getPresion_min() {
        return presion_min;
    }

    public void setPresion_min(double presion_min) {
        this.presion_min = presion_min;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public int getDesgaste() {
        return desgaste;
    }

    public void setDesgaste(int desgaste) {
        this.desgaste = desgaste;
    }

    public String getIndice_velocidad() {
        return indice_velocidad;
    }

    public void setIndice_velocidad(String indice_velocidad) {
        this.indice_velocidad = indice_velocidad;
    }

    public String getIndice_carga() {
        return indice_carga;
    }

    public void setIndice_carga(String indice_carga) {
        this.indice_carga = indice_carga;
    }

    public double getAltura_min() {
        return altura_min;
    }

    public void setAltura_min(double altura_min) {
        this.altura_min = altura_min;
    }

    public double getAltura_max() {
        return altura_max;
    }

    public void setAltura_max(double altura_max) {
        this.altura_max = altura_max;
    }

    public String getRadial() {
        return radial;
    }

    public void setRadial(String radial) {
        this.radial = radial;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
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

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public int getTipo_neumatico_ID() {
        return tipo_neumatico_ID;
    }

    public void setTipo_neumatico_ID(int tipo_neumatico_ID) {
        this.tipo_neumatico_ID = tipo_neumatico_ID;
    }

    private String dot;

    private String fecmod;
    private String usumod;
    private String fecreg;
    private String usureg;
    private String estado;
    private double presion_max;
    private double presion_min;
    private double precio;
    private double temperatura;
    private int desgaste;
    private String indice_velocidad;
    private String indice_carga;
    private double altura_min;
    private double altura_max;
    private String radial;
    private String perfil;
    private int ancho;
    private String modelo;

    private String marca;
    private int empresa_id;
    private int tipo_neumatico_ID;
    public TipoNeumatico(Parcel in) {
        dot = in.readString();
        fecmod = in.readString();
        usumod = in.readString();
        fecreg = in.readString();
        usureg = in.readString();
        estado = in.readString();
        presion_max = in.readDouble();
        presion_min = in.readDouble();
        precio = in.readDouble();
        temperatura = in.readDouble();
        desgaste = in.readInt();
        indice_velocidad = in.readString();
        indice_carga = in.readString();
        altura_min = in.readDouble();
        altura_max = in.readDouble();
        radial = in.readString();
        perfil = in.readString();
        ancho = in.readInt();
        modelo = in.readString();
        marca = in.readString();
        empresa_id = in.readInt();
        tipo_neumatico_ID = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dot);
        dest.writeString(fecmod);
        dest.writeString(usumod);
        dest.writeString(fecreg);
        dest.writeString(usureg);
        dest.writeString(estado);
        dest.writeDouble(presion_max);
        dest.writeDouble(presion_min);
        dest.writeDouble(precio);
        dest.writeDouble(temperatura);
        dest.writeInt(desgaste);
        dest.writeString(indice_velocidad);
        dest.writeString(indice_carga);
        dest.writeDouble(altura_min);
        dest.writeDouble(altura_max);
        dest.writeString(radial);
        dest.writeString(perfil);
        dest.writeInt(ancho);
        dest.writeString(modelo);
        dest.writeString(marca);
        dest.writeInt(empresa_id);
        dest.writeInt(tipo_neumatico_ID);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TipoNeumatico> CREATOR = new Parcelable.Creator<TipoNeumatico>() {
        @Override
        public TipoNeumatico createFromParcel(Parcel in) {
            return new TipoNeumatico(in);
        }

        @Override
        public TipoNeumatico[] newArray(int size) {
            return new TipoNeumatico[size];
        }
    };


    @Override
    public String toString() {
        return "TipoNeumatico{" +
                "dot='" + dot + '\'' +
                ", fecmod='" + fecmod + '\'' +
                ", usumod='" + usumod + '\'' +
                ", fecreg='" + fecreg + '\'' +
                ", usureg='" + usureg + '\'' +
                ", estado='" + estado + '\'' +
                ", presion_max=" + presion_max +
                ", presion_min=" + presion_min +
                ", precio=" + precio +
                ", temperatura=" + temperatura +
                ", desgaste=" + desgaste +
                ", indice_velocidad='" + indice_velocidad + '\'' +
                ", indice_carga='" + indice_carga + '\'' +
                ", altura_min=" + altura_min +
                ", altura_max=" + altura_max +
                ", radial='" + radial + '\'' +
                ", perfil='" + perfil + '\'' +
                ", ancho=" + ancho +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", empresa_id=" + empresa_id +
                ", tipo_neumatico_ID=" + tipo_neumatico_ID +
                '}';
    }





}

