package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;
import java.util.Arrays;

public class Approval extends ElectoralSystem {
	private double mean, stdev;
	
	public Approval(double mean, double stdev) {
		super("Approval");
		this.mean = mean;
		this.stdev = stdev;
	}
	
	private void count(int[] votes, double[] vote) {
		double threshold = Utilities.randomLogNormal(mean, stdev);
		for (int i = 0; i < votes.length; i++) {
			if (vote[i] < threshold) {
				votes[i]++;
			}
		}
	}
	
	public Candidate elect(Populace populace, Vector<Candidate> ballot) {
		int[] votes = new int[ballot.size()];
		Arrays.fill(votes, 0);
		int n = populace.getSize();
		for (int i = 0; i < n; i++) {
			Voter voter = populace.getRandomVoter();
			double[] vote = voter.vote(ballot);
			count(votes, vote);
		}
		return ballot.get(Utilities.max(votes));
	}
}
