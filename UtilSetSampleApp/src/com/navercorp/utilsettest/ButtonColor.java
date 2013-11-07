package com.navercorp.utilsettest;

public class ButtonColor {
	private String name;
	private int c, e;
	private int textColor;
	
	ButtonColor(String name, int c, int e, int textColor) {
		this.name = name;
		this.c = c;
		this.e = e;
		this.textColor = textColor;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getC() {
		return c;
	}
	public void setC(int c) {
		this.c = c;
	}
	public int getE() {
		return e;
	}
	public void setE(int e) {
		this.e = e;
	}
	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}
	
}
