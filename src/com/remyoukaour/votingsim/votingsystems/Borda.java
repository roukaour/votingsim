package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;
import java.util.Arrays;

public class Borda extends ElectoralSystem {
	public Borda() {
		super("Borda");
	}
	
	private void count(int[] votes, double[] vote) {
		for (int i = 0; i < vote.length; i++) {
			for (int j = 0; j < vote.length; j++) {
				if (j != i && vote[j] < vote[i]) {
					votes[i]++;
				}
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
		return ballot.get(Utilities.min(votes));
	}
}
