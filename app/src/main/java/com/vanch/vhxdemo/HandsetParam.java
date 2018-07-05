package com.vanch.vhxdemo;

/**
 * 设备参数
 * @author Administrator
 */
public class HandsetParam {
    public byte TagType;        //标签种类：01H－ISO18000-6B，02H－EPCC1，04H－EPCC1G2，08H－EM4442。
    public byte Alarm;            //bit0-bit7 bit0:0-不报警，1-报警 bit1 0-不使用白名单 1-使用白名单。 0/1
    public byte OutputMode;        //数据输出模式：0-保存并直接输出,1-保存但不直接输出,2-不保存但直接输出
    public byte USBBaudRate;    //USB接口波特率 04H--08H
    public byte Reserve5;        //保留
    public byte Min_Frequence;    //发射微波信号频率的起始点，取值： 1~63。
    public byte Max_Frequence;    //发射微波信号频率的起始点，取值： 1~63。
    public byte Power;            //发射功率值，取值：0~160。
    public byte RFhrdVer1;        //RF模块硬件主版本
    public byte RFhrdVer2;        //RF模块硬件次版本
    public byte RFSoftVer1;        //RF模块软件主版本
    public byte RFSoftVer2;        //RF模块软件次版本
    public byte ISTID;            //是否读TID区
    public byte TIDAddr;        //TID读取起始位置
    public byte TIDLen;            //TID读取长度
    public byte ISUSER;            //是否读USER
    public byte USERAddr;        //USER读取起始位置
    public byte USERLen;        //USER读取长度
    public byte Reserve19;        //保留
    public byte Reserve20;        //保留
    public byte Reserve21;        //保留
    public byte Reserve22;        //保留
    public byte Reserve23;        //保留
    public byte Reserve24;        //保留
    public byte Reserve25;        //保留
    public byte Reserve26;        //保留
    public byte Reserve27;        //保留
    public byte Reserve28;        //保留
    public byte Reserve29;        //保留
    public byte Reserve30;        //保留
    public byte Reserve31;        //保留
    public byte Reserve32;        //保留

    public byte[] toBytes() {

        byte[] data = new byte[32];
        int index = 0;

        data[index++] = (byte) TagType;
        data[index++] = (byte) Alarm;
        data[index++] = (byte) OutputMode;
        data[index++] = (byte) USBBaudRate;
        data[index++] = (byte) Reserve5;
        data[index++] = (byte) Min_Frequence;
        data[index++] = (byte) Max_Frequence;
        data[index++] = (byte) Power;
        data[index++] = (byte) RFhrdVer1;
        data[index++] = (byte) RFhrdVer2;
        data[index++] = (byte) RFSoftVer1;
        data[index++] = (byte) RFSoftVer2;
        data[index++] = (byte) ISTID;
        data[index++] = (byte) TIDAddr;
        data[index++] = (byte) TIDLen;
        data[index++] = (byte) ISUSER;
        data[index++] = (byte) USERAddr;
        data[index++] = (byte) USERLen;
        data[index++] = (byte) Reserve19;
        data[index++] = (byte) Reserve20;
        data[index++] = (byte) Reserve21;
        data[index++] = (byte) Reserve22;
        data[index++] = (byte) Reserve23;
        data[index++] = (byte) Reserve24;
        data[index++] = (byte) Reserve25;
        data[index++] = (byte) Reserve26;
        data[index++] = (byte) Reserve27;
        data[index++] = (byte) Reserve28;
        data[index++] = (byte) Reserve29;
        data[index++] = (byte) Reserve30;
        data[index++] = (byte) Reserve31;
        data[index++] = (byte) Reserve32;

        return data;
    }

    public String getRFVersion() {
        return "硬件版本 " + RFhrdVer1 + "." + RFhrdVer2 + "，软件版本 " +
                RFSoftVer1 + "." + RFSoftVer2;
    }

    public boolean isAlarm() {
        return (Alarm & 0x01) == 0x01; //00000001
    }

    public boolean isLabel() {
        return (Alarm & 0x02) == 0x02; //00000010
//        return ((Alarm >> 1) & 0x01) == 0x02;
    }

    public void setAlarm(boolean isAlarm, boolean isLabel) {
        int alarm = 1;
        if (!isAlarm)
            alarm = 0;

        if (isLabel)
            alarm += 2;

        Alarm = (byte) alarm;
    }

    public boolean isTID() {
        return ISTID != 0;
    }

    public void setTID(boolean isTID) {
        if (isTID) {
            ISTID = 1;
        } else {
            ISTID = 0;
        }
    }

    public boolean isUSER() {
        return ISUSER != 0;
    }

    public void setUSER(boolean isUser) {
        if (isUser) {
            ISUSER = 1;
        } else {
            ISUSER = 0;
        }
    }

    @Override
    public String toString() {
        return "HandsetParam{" + "TagType=" + TagType + ", Alarm=" + Alarm + ", OutputMode=" + OutputMode + ", USBBaudRate=" + USBBaudRate + ", Reserve5=" + Reserve5 + ", Min_Frequence=" + Min_Frequence + ", Max_Frequence=" + Max_Frequence + ", Power=" + Power + ", RFhrdVer1=" + RFhrdVer1 + ", RFhrdVer2=" + RFhrdVer2 + ", RFSoftVer1=" + RFSoftVer1 + ", RFSoftVer2=" + RFSoftVer2 + ", ISTID=" + ISTID + ", TIDAddr=" + TIDAddr + ", TIDLen=" + TIDLen + ", ISUSER=" + ISUSER + ", USERAddr=" + USERAddr + ", USERLen=" + USERLen + ", Reserve19=" + Reserve19 + ", Reserve20=" + Reserve20 + ", Reserve21=" + Reserve21 + ", Reserve22=" + Reserve22 + ", Reserve23=" + Reserve23 + ", Reserve24=" + Reserve24 + ", Reserve25=" + Reserve25 + ", Reserve26=" + Reserve26 + ", Reserve27=" + Reserve27 + ", Reserve28=" + Reserve28 + ", Reserve29=" + Reserve29 + ", Reserve30=" + Reserve30 + ", Reserve31=" + Reserve31 + ", Reserve32=" + Reserve32 + '}';
    }

}
