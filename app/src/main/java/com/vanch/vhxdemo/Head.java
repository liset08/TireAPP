/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vanch.vhxdemo;

/**
 * 命令头
 * @author Administrator
 */
public enum Head {
    SEND((byte)0x40), RECEIVE_OK((byte)0xf0), RECEIVE_FAIL((byte)0xf4);
    byte code;

    private Head(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
