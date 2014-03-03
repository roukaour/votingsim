package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;
import java.util.Arrays;

public class Concordet extends ElectoralSystem {
	public Concordet() {
		super("Concordet (Schulze)");
	}
	
	private void count(int[][] votes, double[] vote) {
		for (int i = 0; i < vote.length; i++) {
			for (int j = 0; j < vote.length; j++) {
				if (j != i && vote[i] < vote[j]) {
					votes[i][j]++;
				}
			}
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
		int[][] strengths = new int[votes.length][votes.length];
		for (int i = 0; i < strengths.length; i++) {
			Arrays.fill(strengths[i], 0);
		}
		for (int i = 0; i < votes.length; i++) {
			for (int j = 0; j < votes.length; j++) {
				if (i == j)
					continue;
				strengths[i][j] = votes[i][j] > votes[j][i] ? votes[i][j] : 0;
			}
		}
		for (int i = 0; i < strengths.length; i++) {
			for (int j = 0; j < strengths.length; j++) {
				if (i == j)
					continue;
				for (int k = 0; k < strengths.length; k++) {
					if (i == k || j == k)
						continue;
					strengths[j][k] = Math.max(strengths[j][k],
							Math.min(strengths[j][i], strengths[i][k]));
				}
			}
		}
		Vector<Integer> winners = new Vector<Integer>();
		for (int i = 0; i < strengths.length; i++) {
			int j = 0;
			for (; j < strengths.length; j++) {
				if (strengths[i][j] < strengths[j][i])
					break;
			}
			if (j == strengths.length) {
				winners.add(i);
			}
		}
		int winner = winners.get(Utilities.randomRange(winners.size()));
		return ballot.get(winner);
	}
}
