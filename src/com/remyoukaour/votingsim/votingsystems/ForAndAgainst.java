package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;
import java.util.Arrays;

public class ForAndAgainst extends ElectoralSystem {
	public ForAndAgainst() {
		super("For-and-against");
	}
	
	private void count(int[] votes, double[] vote) {
		votes[Utilities.min(vote)]++;
		votes[Utilities.max(vote)]--;
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
