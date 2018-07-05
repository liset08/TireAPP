package com.com.vanch.vhxdemo.helper;
import java.nio.ByteBuffer;
import java.util.Random;

public class Test {
	public static String randomString() {
		String s="";
		Random r = new Random();
		for(int i = 0; i<16;i++ ) {
			int c = 'a' + r.nextInt(26);
			s=s+(char)c;
		}
		return s;
	}
	
	public static byte[] randomBytes() {
		ByteBuffer bb = ByteBuffer.allocate(16);
		Random r = new Random();
		for(int i = 0; i<16;i++ ) {
			int c = r.nextInt(255);
			bb.put((byte) c);
		}
		return bb.array();
	}
}
