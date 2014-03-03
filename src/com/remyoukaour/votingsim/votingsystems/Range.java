package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;
import java.util.Arrays;

public class Range extends ElectoralSystem {
	public Range() {
		super("Range");
	}
	
	private void count(double[] votes, double[] vote) {
		for (int i = 0; i < votes.length; i++)
			votes[i] += vote[i];
	}
	
	public Candidate elect(Populace populace, Vector<Candidate> ballot) {
		double[] votes = new double[ballot.size()];
		Arrays.fill(votes, 0.0);
		int n = populace.getSize();
		for (int i = 0; i < n; i++) {
			Voter voter = populace.getRandomVoter();
			double[] vote = voter.vote(ballot);
			count(votes, vote);
		}
		return ballot.get(Utilities.min(votes));
	}
}
