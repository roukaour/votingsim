package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;

public class RandomDictator extends ElectoralSystem {
	public RandomDictator() {
		super("Random dictator");
	}
	
	public Candidate elect(Populace populace, Vector<Candidate> ballot) {
		Voter voter = populace.getRandomVoter();
		double[] vote = voter.vote(ballot);
		return ballot.get(Utilities.min(vote));
	}
}
