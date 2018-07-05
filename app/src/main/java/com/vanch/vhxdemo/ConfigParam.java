package com.vanch.vhxdemo;

public class ConfigParam {
	boolean autolink;
	boolean detectsound;
	boolean skipname;
	boolean singletag;
	boolean checkshock;
	
	int s,ab,q, timeout,power;
	String swVersion,hwVersion;
	public boolean isAutolink() {
		return autolink;
	}
	public void setAutolink(boolean autolink) {
		this.autolink = autolink;
	}
	public boolean isDetectsound() {
		return detectsound;
	}
	public void setDetectsound(boolean detectsound) {
		this.detectsound = detectsound;
	}
	public boolean isSkipname() {
		return skipname;
	}
	public void setSkipname(boolean skipname) {
		this.skipname = skipname;
	}
	public boolean isSingletag() {
		return singletag;
	}
	public void setSingletag(boolean singletag) {
		this.singletag = singletag;
	}
	public boolean isCheckshock() {
		return checkshock;
	}
	public void setCheckshock(boolean checkshock) {
		this.checkshock = checkshock;
	}
	public int getS() {
		return s;
	}
	public void setS(int s) {
		this.s = s;
	}
	public int getAb() {
		return ab;
	}
	public void setAb(int ab) {
		this.ab = ab;
	}
	public int getQ() {
		return q;
	}
	public void setQ(int q) {
		this.q = q;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public String getSwVersion() {
		return swVersion;
	}
	public void setSwVersion(String swVersion) {
		this.swVersion = swVersion;
	}
	public String getHwVersion() {
		return hwVersion;
	}
	public void setHwVersion(String hwVersion) {
		this.hwVersion = hwVersion;
	}
	@Override
	public String toString() {
		return "ConfigParam [autolink=" + autolink + ", detectsound=" + detectsound
				+ ", skipname=" + skipname + ", singletag=" + singletag
				+ ", checkshock=" + checkshock + ", s=" + s + ", ab=" + ab + ", q=" + q
				+ ", timeout=" + timeout + ", power=" + power + ", swVersion="
				+ swVersion + ", hwVersion=" + hwVersion + "]";
	}
}
