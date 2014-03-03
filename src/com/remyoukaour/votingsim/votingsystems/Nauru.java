package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;
import java.util.Arrays;

public class Nauru extends ElectoralSystem {
	public Nauru() {
		super("Nauru");
	}
	
	private void count(double[] votes, double[] vote) {
		int[] ranks = new int[vote.length];
		Arrays.fill(ranks, 0);
		for (int i = 0; i < vote.length; i++) {
			for (int j = 0; j < vote.length; j++) {
				if (j != i && vote[j] < vote[i]) {
					ranks[i]++;
				}
			}
		}
		for (int i = 0; i < votes.length; i++) {
			votes[i] += 1.0 / (ranks[i] + 1.0);
		}
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
		return ballot.get(Utilities.max(votes));
	}
}
