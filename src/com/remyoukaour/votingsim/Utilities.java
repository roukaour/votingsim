package com.remyoukaour.votingsim;

import java.util.Random;
import java.util.Vector;
import java.awt.Color;

public class Utilities {
	private static final Random random = new Random();
	
	public static int min(int[] items) {
		if (items.length == 0)
			return -1;
		int maxi = items[0];
		Vector<Integer> indexes = new Vector<Integer>();
		indexes.addElement(0);
		for (int i = 1; i < items.length; i++) {
			if (items[i] < maxi) {
				maxi = items[i];
				indexes.clear();
				indexes.addElement(i);
			}
			else if (items[i] == maxi) {
				indexes.addElement(i);
			}
		}
		int i = randomRange(indexes.size());
		return indexes.get(i);
	}
	
	public static int min(double[] items) {
		if (items.length == 0)
			return -1;
		double maxi = items[0];
		Vector<Integer> indexes = new Vector<Integer>();
		indexes.addElement(0);
		for (int i = 1; i < items.length; i++) {
			if (items[i] < maxi) {
				maxi = items[i];
				indexes.clear();
				indexes.addElement(i);
			}
			else if (items[i] == maxi) {
				indexes.addElement(i);
			}
		}
		int i = randomRange(indexes.size());
		return indexes.get(i);
	}
	
	public static int max(int[] items) {
		if (items.length == 0)
			return -1;
		int maxi = items[0];
		Vector<Integer> indexes = new Vector<Integer>();
		indexes.addElement(0);
		for (int i = 1; i < items.length; i++) {
			if (items[i] > maxi) {
				maxi = items[i];
				indexes.clear();
				indexes.addElement(i);
			}
			else if (items[i] == maxi) {
				indexes.addElement(i);
			}
		}
		int i = randomRange(indexes.size());
		return indexes.get(i);
	}
	
	public static int max(double[] items) {
		if (items.length == 0)
			return -1;
		double maxi = items[0];
		Vector<Integer> indexes = new Vector<Integer>();
		indexes.addElement(0);
		for (int i = 1; i < items.length; i++) {
			if (items[i] > maxi) {
				maxi = items[i];
				indexes.clear();
				indexes.addElement(i);
			}
			else if (items[i] == maxi) {
				indexes.addElement(i);
			}
		}
		int i = randomRange(indexes.size());
		return indexes.get(i);
	}
	
	public static double average(double[] items) {
		double total = 0.0;
		for (int i = 0; i < items.length; i++)
			total += items[i];
		return total / items.length;
	}
	
	public static double[] without(double[] items, int r) {
		double[] subitems = new double[items.length-1];
		int passed = 0;
		for (int i = 0; i < subitems.length; i++) {
			if (i == r)
				passed = 1;
			subitems[i] = items[i+passed];
		}
		return subitems;
	}
	
	public static int randomRange(int stop) {
		return random.nextInt(stop);
	}
	
	public static int randomRange(int start, int stop) {
		return random.nextInt(stop-start) + start;
	}
	
	public static double randomNormal(double mean, double stdev) {
		return random.nextGaussian() * stdev + mean;
	}
	
	public static double randomLogNormal(double mean, double stdev) {
		return Math.exp(randomNormal(mean, stdev));
	}
	
	public static boolean randomBoolean() {
		return random.nextBoolean();
	}
	
	public static Color randomColor() {
		int r = randomRange(256);
		int g = randomRange(256);
		int b = randomRange(256);
		return new Color(r, g, b);
	}
	
	public static String colorToString(Color c) {
		return String.format("%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
	}
	
	public static String rgbToString(int rgb) {
		return colorToString(new Color(rgb));
	}
	
	public static Color stringToColor(String s) {
		int r = Integer.parseInt(s.substring(0, 2), 16);
		int g = Integer.parseInt(s.substring(2, 4), 16);
		int b = Integer.parseInt(s.substring(4, 6), 16);
		return new Color(r, g, b);
	}
	
	public static int stringToRGB(String s) {
		return stringToColor(s).getRGB();
	}
}