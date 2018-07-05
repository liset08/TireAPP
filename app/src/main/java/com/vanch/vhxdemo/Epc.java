package com.vanch.vhxdemo;

public class Epc {
	private String id;
	private int count;
	
	public Epc(String id, int count) {
		this.id = id;
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Epc [id=" + id + ", count=" + count + "]";
	}
}
