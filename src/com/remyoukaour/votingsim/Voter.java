package com.remyoukaour.votingsim;

import java.awt.geom.Point2D;
import java.util.Vector;

public class Voter extends Point2D.Double {
	private static final long serialVersionUID = -93110123920636907L;

	public Voter(double x, double y) {
		super(x, y);
	}
	
	public double[] vote(Vector<Candidate> ballot) {
		double[] distances = new double[ballot.size()];
		for (int i = 0; i < distances.length; i++)
			distances[i] = distance(ballot.get(i));
		return distances;
	}
}
