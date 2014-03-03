package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;
import java.util.Arrays;

public class Contingent extends ElectoralSystem {
	public Contingent() {
		super("Contingent");
	}
	
	private void count(int[] votes, int[] votes2, double[] vote) {
		int winner = Utilities.min(vote);
		votes[winner]++;
		vote[winner] = Double.POSITIVE_INFINITY;
		int winner2 = Utilities.min(vote);
		votes2[winner2]++;
	}
	
	public Candidate elect(Populace populace, Vector<Candidate> ballot) {
		int[] votes = new int[ballot.size()];
		int[] votes2 = new int[ballot.size()];
		Arrays.fill(votes, 0);
		Arrays.fill(votes2, 0);
		int n = populace.getSize();
		for (int i = 0; i < n; i++) {
			Voter voter = populace.getRandomVoter();
			double[] vote = voter.vote(ballot);
			count(votes, votes2, vote);
		}
		int winner = Utilities.max(votes);
		if (votes[winner] * 2 > n)
			return ballot.get(winner);
		int winnerVotes = votes[winner] + votes2[winner];
		votes[winner] = Integer.MIN_VALUE;
		int winner2 = Utilities.max(votes);
		int winner2Votes = votes[winner2] + votes2[winner2];
		if (winnerVotes > winner2Votes)
			return ballot.get(winner);
		if (winnerVotes < winner2Votes)
			return ballot.get(winner2);
		return ballot.get(Utilities.randomBoolean() ? winner : winner2);
	}
}
