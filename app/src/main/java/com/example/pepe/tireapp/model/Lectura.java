package com.example.pepe.tireapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LISET on 08/08/2018.
 */

public class Lectura implements Parcelable {

    @Override
    public String toString() {
        return "Lectura{" +
                "fecmod='" + fecmod + '\'' +
                ", usumod='" + usumod + '\'' +
                ", fecreg='" + fecreg + '\'' +
                ", usureg='" + usureg + '\'' +
                ", estado='" + estado + '\'' +
                ", observacion='" + observacion + '\'' +
                ", Kilometraje_Neu=" + Kilometraje_Neu +
                ", varianza=" + varianza +
                ", prom_desgaste=" + prom_desgaste +
                ", desgaste_3=" + desgaste_3 +
                ", desgaste_2=" + desgaste_2 +
                ", desgaste_1=" + desgaste_1 +
                ", presion=" + presion +
                ", NeumaticoID=" + NeumaticoID +
                ", CamionID=" + CamionID +
                ", sesion=" + sesion +
                ", LecturaID=" + LecturaID +
                '}';
    }

    private String fecmod;
    private String usumod;
    private String fecreg;
    private String usureg;
    private String estado;
    private String observacion;
    private double Kilometraje_Neu;
    private double varianza;

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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public double getKilometraje_Neu() {
        return Kilometraje_Neu;
    }

    public void setKilometraje_Neu(double kilometraje_Neu) {
        Kilometraje_Neu = kilometraje_Neu;
    }

    public double getVarianza() {
        return varianza;
    }

    public void setVarianza(double varianza) {
        this.varianza = varianza;
    }

    public double getProm_desgaste() {
        return prom_desgaste;
    }

    public void setProm_desgaste(double prom_desgaste) {
        this.prom_desgaste = prom_desgaste;
    }

    public double getDesgaste_3() {
        return desgaste_3;
    }

    public void setDesgaste_3(double desgaste_3) {
        this.desgaste_3 = desgaste_3;
    }

    public double getDesgaste_2() {
        return desgaste_2;
    }

    public void setDesgaste_2(double desgaste_2) {
        this.desgaste_2 = desgaste_2;
    }

    public double getDesgaste_1() {
        return desgaste_1;
    }

    public void setDesgaste_1(double desgaste_1) {
        this.desgaste_1 = desgaste_1;
    }

    public double getPresion() {
        return presion;
    }

    public void setPresion(double presion) {
        this.presion = presion;
    }

    public int getNeumaticoID() {
        return NeumaticoID;
    }

    public void setNeumaticoID(int neumaticoID) {
        NeumaticoID = neumaticoID;
    }

    public int getCamionID() {
        return CamionID;
    }

    public void setCamionID(int camionID) {
        CamionID = camionID;
    }

    public int getSesion() {
        return sesion;
    }

    public void setSesion(int sesion) {
        this.sesion = sesion;
    }

    public int getLecturaID() {
        return LecturaID;
    }

    public void setLecturaID(int lecturaID) {
        LecturaID = lecturaID;
    }

    private double prom_desgaste;
    private double desgaste_3;
    private double desgaste_2;
    private double desgaste_1;
    private double presion;
    private int NeumaticoID;
    private int CamionID;
    private int sesion;
    private int LecturaID;

    public Lectura() {

    }



    protected Lectura(Parcel in) {
        fecmod = in.readString();
        usumod = in.readString();
        fecreg = in.readString();
        usureg = in.readString();
        estado = in.readString();
        observacion = in.readString();
        Kilometraje_Neu = in.readDouble();
        varianza = in.readDouble();
        prom_desgaste = in.readDouble();
        desgaste_3 = in.readDouble();
        desgaste_2 = in.readDouble();
        desgaste_1 = in.readDouble();
        presion = in.readDouble();
        NeumaticoID = in.readInt();
        CamionID = in.readInt();
        sesion = in.readInt();
        LecturaID = in.readInt();
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
        dest.writeString(estado);
        dest.writeString(observacion);
        dest.writeDouble(Kilometraje_Neu);
        dest.writeDouble(varianza);
        dest.writeDouble(prom_desgaste);
        dest.writeDouble(desgaste_3);
        dest.writeDouble(desgaste_2);
        dest.writeDouble(desgaste_1);
        dest.writeDouble(presion);
        dest.writeInt(NeumaticoID);
        dest.writeInt(CamionID);
        dest.writeInt(sesion);
        dest.writeInt(LecturaID);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Lectura> CREATOR = new Parcelable.Creator<Lectura>() {
        @Override
        public Lectura createFromParcel(Parcel in) {
            return new Lectura(in);
        }

        @Override
        public Lectura[] newArray(int size) {
            return new Lectura[size];
        }
    };
}