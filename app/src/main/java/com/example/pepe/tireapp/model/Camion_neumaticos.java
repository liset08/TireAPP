package com.example.pepe.tireapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LISET on 06/08/2018.
 */

public class Camion_neumaticos implements Parcelable {

    private String fecmod;
    private String usumod;
    private String fecreg;
    private String usureg;
    private String estadouso;
    private String posicion;
    private double Kilometraje_act;
    private int neumatico_id;
    private int camion_id;
    private int camion_neum_ID;

    public Camion_neumaticos(){}

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

    public String getEstadouso() {
        return estadouso;
    }

    public void setEstadouso(String estadouso) {
        this.estadouso = estadouso;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public double getKilometraje_act() {
        return Kilometraje_act;
    }

    public void setKilometraje_act(double Kilometraje_act) {
        this.Kilometraje_act = Kilometraje_act;
    }

    public int getNeumatico_id() {
        return neumatico_id;
    }

    public void setNeumatico_id(int neumatico_id) {
        this.neumatico_id = neumatico_id;
    }

    public int getCamion_id() {
        return camion_id;
    }

    public void setCamion_id(int camion_id) {
        this.camion_id = camion_id;
    }

    public int getCamion_neum_ID() {
        return camion_neum_ID;
    }

    public void setCamion_neum_ID(int camion_neum_ID) {
        this.camion_neum_ID = camion_neum_ID;
    }

    public Camion_neumaticos(Parcel in) {
        fecmod = in.readString();
        usumod = in.readString();
        fecreg = in.readString();
        usureg = in.readString();
        estadouso = in.readString();
        posicion = in.readString();
        Kilometraje_act = in.readDouble();
        neumatico_id = in.readInt();
        camion_id = in.readInt();
        camion_neum_ID = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fecmod);
        dest.writeString(usumod);
        dest.writeString(fecreg);
        dest.writeString(usureg);
        dest.writeString(estadouso);
        dest.writeString(posicion);
        dest.writeDouble(Kilometraje_act);
        dest.writeInt(neumatico_id);
        dest.writeInt(camion_id);
        dest.writeInt(camion_neum_ID);
    }

    @SuppressWarnings("unused")
    public static final Creator<Camion_neumaticos> CREATOR = new Creator<Camion_neumaticos>() {
        @Override
        public Camion_neumaticos createFromParcel(Parcel in) {
            return new Camion_neumaticos(in);
        }

        @Override
        public Camion_neumaticos[] newArray(int size) {
            return new Camion_neumaticos[size];
        }
    };
}