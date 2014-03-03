package com.remyoukaour.votingsim;

import java.awt.Color;
import java.awt.geom.Point2D;

public class Candidate extends Point2D.Double {
	private static final long serialVersionUID = -7408758850861873827L;
	
	private Color color;
	
	public Candidate(double x, double y, Color color) {
		super(x, y);
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public String toString() {
		return String.format("<html><span style=\"color: #%s;\">&#9608;</span>" +
				" (%.2f, %.2f)</html>", Utilities.colorToString(color), x, y);
	}
}
