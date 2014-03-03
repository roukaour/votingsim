package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;
import java.util.Arrays;

public class Dabagh extends ElectoralSystem {
	public Dabagh() {
		super("Dabagh");
	}
	
	private void count(int[] votes, double[] vote) {
		int winner = Utilities.min(vote);
		votes[winner] += 2;
		vote[winner] = Double.POSITIVE_INFINITY;
		int winner2 = Utilities.min(vote);
		votes[winner2]++;
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
