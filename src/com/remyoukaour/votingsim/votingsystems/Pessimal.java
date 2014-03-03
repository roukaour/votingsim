package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;

public class Pessimal extends ElectoralSystem {
	public Pessimal() {
		super("Pessimal");
	}
	
	public Candidate elect(Populace populace, Vector<Candidate> ballot) {
		Voter voter = new Voter(populace.getMeanX(), populace.getMeanY());
		double[] vote = voter.vote(ballot);
		return ballot.get(Utilities.max(vote));
	}
}
