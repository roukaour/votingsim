package com.remyoukaour.votingsim;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.swing.*;

public class ImagePanel extends JComponent {
	public static final long serialVersionUID = 1625271077L;
	
	private double xmin, xmax, ymin, ymax, aspect;
	private int width, height;
	private BufferedImage image;
	private Graphics2D graphics;
	
	public ImagePanel(Color background, double xmin, double xmax, double ymin, double ymax) {
		setBackground(background);
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
		this.aspect = (xmax - xmin) / (ymax - ymin);
	}
	
	public void paintComponent(Graphics g) {
		if (image == null)
			setupImage();
		g.drawImage(image, 0, 0, null);
	}
	
	public int setWidth(int width) {
		int height = (int)(width / aspect);
		setSize(new Dimension(width, height));
		setupImage();
		return height;
	}
	
	public int setHeight(int height) {
		int width = (int)(height * aspect);
		setSize(new Dimension(width, height));
		setupImage();
		return width;
	}
	
	public Point2D.Double getPoint(int x, int y) {
		double px = xmin + (xmax - xmin) * x / width;
		double py = ymin + (ymax - ymin) * (height - y - 1) / height;
		return new Point2D.Double(px, py);
	}
	
	public Point getCoords(double x, double y) {
		int px = (int)((x - xmin) / (xmax - xmin) * width);
		int py = height - (int)((y - ymin) / (ymax - ymin) * height) - 1;
		return new Point(px, py);
	}
	
	public void setupImage() {
		width = getSize().width;
		height = getSize().height;
		setPreferredSize(getSize());
		BufferedImage oldImage = image;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		if (graphics != null)
			graphics.dispose();
		graphics = (Graphics2D)image.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		clearImage();
		if (oldImage != null)
			graphics.drawImage(oldImage, 0, 0, null);
	}
	
	public void clearImage() {
		if (graphics != null) {
			graphics.setPaint(getBackground());
			graphics.fillRect(0, 0, width, height);
		}
		paintImmediately(0, 0, width, height);
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void putPoint(double x, double y, Color color) {
		Point p = getCoords(x, y);
		putPoint(p.getX(), p.getY(), color);
	}
	
	public void putPoint(int x, int y, Color color) {
		if (graphics != null) {
			graphics.setColor(color);
			graphics.drawRect(x, y, 1, 1);
		}
		paintImmediately(x, y, 1, 1);
	}
	
	public void putMark(double x, double y, Color color) {
		Point p = getCoords(x, y);
		putMark((int)p.getX(), (int)p.getY(), color);
	}
	
	public void putMark(int x, int y, Color color) {
		if (graphics != null) {
			graphics.setColor(Color.WHITE);
			graphics.fillOval(x-4, y-4, 8, 8);
			graphics.setColor(Color.BLACK);
			graphics.drawOval(x-4, y-4, 8, 8);
			graphics.setColor(color);
			graphics.fillOval(x-2, y-2, 5, 5);
		}
		paintImmediately(x-5, y-5, 11, 11);
	}
}