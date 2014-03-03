package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;
import java.util.Arrays;

public class StrategicApproval extends ElectoralSystem {
	public StrategicApproval() {
		super("Approval (strategic)");
	}
	
	private void count(int[] votes, double[] vote) {
		double threshold = Utilities.average(vote);
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
