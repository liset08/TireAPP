package com.vanch.vhxdemo;

/**
 * 设备版本信息
 * @author Administrator
 */
public class HandsetVersion {
    public byte hdVer1;
    public byte hdVer2;

    public  byte swVer1;
    public  byte swVer2;

    @Override
    public String toString() {
        return "HandsetVersion{" + "hdVer1=" + hdVer1 + ", hdVer2=" + hdVer2 + ", swVer1=" + swVer1 + ", swVer2=" + swVer2 + '}';
    }
    
    public String getHwString() {
        return hdVer1*0x100+""+hdVer2;
    }
    
    public String getSwString() {
        return swVer1*0x100+""+swVer2;
    }
}
