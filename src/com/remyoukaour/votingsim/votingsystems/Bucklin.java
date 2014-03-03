package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;
import java.util.Arrays;

public class Bucklin extends ElectoralSystem {
	public Bucklin() {
		super("Bucklin");
	}
	
	private void count(int[][] votes, double[] vote) {
		int[] ranks = new int[vote.length];
		Arrays.fill(ranks, 0);
		for (int i = 0; i < vote.length; i++) {
			for (int j = 0; j < vote.length; j++) {
				if (j != i && vote[j] < vote[i]) {
					ranks[i]++;
				}
			}
		}
		for (int i = 0; i < ranks.length; i++) {
			votes[ranks[i]][i]++;
		}
	}
	
	public Candidate elect(Populace populace, Vector<Candidate> ballot) {
		int[][] votes = new int[ballot.size()][ballot.size()];
		for (int i = 0; i < votes.length; i++) {
			Arrays.fill(votes[i], 0);
		}
		int n = populace.getSize();
		for (int i = 0; i < n; i++) {
			Voter voter = populace.getRandomVoter();
			double[] vote = voter.vote(ballot);
			count(votes, vote);
		}
		int winner = 0;
		for (int round = 0; round < votes.length; round++) {
			winner = Utilities.max(votes[round]);
			if (votes[round][winner] * 2 >= n || round == votes.length - 1)
				break;
			for (int i = 0; i < votes[round].length; i++) {
				votes[round+1][i] += votes[round][i];
			}
		}
		return ballot.get(winner);
	}
}
