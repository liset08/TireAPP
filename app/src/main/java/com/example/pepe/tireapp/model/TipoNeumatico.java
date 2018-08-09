package com.example.pepe.tireapp.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by LISET on 23/07/2018.
 */

public class TipoNeumatico implements Parcelable {


    private String radial;
    private int cant_reencauche;
    private String observacion;
    private String dot;
    private String tag;
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

    @Override
    public String toString() {
        return "TipoNeumatico{" +
                "radial='" + radial + '\'' +
                ", cant_reencauche=" + cant_reencauche +
                ", observacion='" + observacion + '\'' +
                ", dot='" + dot + '\'' +
                ", tag='" + tag + '\'' +
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
                ", perfil='" + perfil + '\'' +
                ", ancho=" + ancho +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", empresa_id=" + empresa_id +
                ", Neumatico_id=" + Neumatico_id +
                '}';
    }

    private String perfil;
    private int ancho;
    private String modelo;
    private String marca;
    private int empresa_id;
    private int Neumatico_id;

    public TipoNeumatico() {

    }

    public String getRadial() {
        return radial;
    }

    public void setRadial(String radial) {
        this.radial = radial;
    }

    public int getCant_reencauche() {
        return cant_reencauche;
    }

    public void setCant_reencauche(int cant_reencauche) {
        this.cant_reencauche = cant_reencauche;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDot() {
        return dot;
    }

    public void setDot(String dot) {
        this.dot = dot;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public int getNeumatico_id() {
        return Neumatico_id;
    }

    public void setNeumatico_id(int Neumatico_id) {
        this.Neumatico_id = Neumatico_id;
    }



    protected TipoNeumatico(Parcel in) {
        radial = in.readString();
        cant_reencauche = in.readInt();
        observacion = in.readString();
        dot = in.readString();
        tag = in.readString();
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
        perfil = in.readString();
        ancho = in.readInt();
        modelo = in.readString();
        marca = in.readString();
        empresa_id = in.readInt();
        Neumatico_id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(radial);
        dest.writeInt(cant_reencauche);
        dest.writeString(observacion);
        dest.writeString(dot);
        dest.writeString(tag);
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
        dest.writeString(perfil);
        dest.writeInt(ancho);
        dest.writeString(modelo);
        dest.writeString(marca);
        dest.writeInt(empresa_id);
        dest.writeInt(Neumatico_id);
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
}