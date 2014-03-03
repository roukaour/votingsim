package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Arrays;
import java.util.Vector;

public class MajorityJudgment extends ElectoralSystem {
	public MajorityJudgment() {
		super("Majority judgment");
	}
	
	private void count(double[][] votes, double[] vote, int i) {
		for (int j = 0; j < vote.length; j++) {
			votes[j][i] = vote[j];
		}
	}
	
	public Candidate elect(Populace populace, Vector<Candidate> ballot) {
		int m = ballot.size();
		int n = populace.getSize();
		double[][] votes = new double[m][n];
		for (int i = 0; i < n; i++) {
			Voter voter = populace.getRandomVoter();
			double[] vote = voter.vote(ballot);
			count(votes, vote, i);
		}
		double[] medians = new double[m];
		int medianIndex = n / 2;
		for (int i = 0; i < m; i++) {
			Arrays.sort(votes[i]);
			medians[i] = votes[i][medianIndex];
		}
		return ballot.get(Utilities.min(medians));
	}
}
