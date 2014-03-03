package com.remyoukaour.votingsim;

import java.awt.geom.Point2D;

public class Populace {
	private final int size;
	private final double xmean, ymean, stdev;
	
	public Populace(int size, Point2D center, double stdev) {
		this(size, center.getX(), center.getY(), stdev);
	}
	
	public Populace(int size, double xmean, double ymean, double stdev) {
		this.size = size;
		this.xmean = xmean;
		this.ymean = ymean;
		this.stdev = stdev;
	}
	
	public int getSize() {
		return size;
	}
	
	public double getMeanX() {
		return xmean;
	}
	
	public double getMeanY() {
		return ymean;
	}
	
	public double getStdDev() {
		return stdev;
	}
	
	public double getRandomX() {
		return Utilities.randomNormal(xmean, stdev);
	}
	
	public double getRandomY() {
		return Utilities.randomNormal(ymean, stdev);
	}
	
	public Voter getRandomVoter() {
		return new Voter(getRandomX(), getRandomY());
	}
}
