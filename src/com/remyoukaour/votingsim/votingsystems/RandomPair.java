package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;
import java.util.Arrays;

public class RandomPair extends ElectoralSystem {
	public RandomPair() {
		super("Random pair");
	}
	
	private void count(int[] votes, double[] vote) {
		votes[Utilities.min(vote)]++;
	}
	
	public Candidate elect(Populace populace, Vector<Candidate> ballot) {
		int c1 = Utilities.randomRange(ballot.size());
		int c2;
		do {
			c2 = Utilities.randomRange(ballot.size());
		} while (c2 == c1);
		Candidate candidate1 = ballot.get(c1);
		Candidate candidate2 = ballot.get(c2);
		ballot = new Vector<Candidate>();
		ballot.add(candidate1);
		ballot.add(candidate2);
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
