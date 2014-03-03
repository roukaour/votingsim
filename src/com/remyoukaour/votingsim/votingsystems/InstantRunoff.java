package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;

public class InstantRunoff extends ElectoralSystem {
	public InstantRunoff() {
		super("Instant runoff");
	}
	
	public Candidate elect(Populace populace, Vector<Candidate> ballot) {
		InstantRunoffNode votes = new InstantRunoffNode(ballot);
		int n = populace.getSize();
		for (int i = 0; i < n; i++) {
			Voter voter = populace.getRandomVoter();
			double[] vote = voter.vote(ballot);
			votes.count(vote);
		}
		return votes.winner();
	}
	
	protected class InstantRunoffNode extends AbstractNode {
		public InstantRunoffNode(Vector<Candidate> ballot) {
			this(null, ballot);
		}
		
		protected InstantRunoffNode(Candidate candidate, Vector<Candidate> ballot) {
			super(candidate, ballot);
			int size = ballot.size();
			for (int i = 0; i < size; i++) {
				Vector<Candidate> subballot = new Vector<Candidate>(ballot);
				Candidate child = subballot.remove(i);
				children.addElement(new InstantRunoffNode(child, subballot));
			}
		}
		
		public AbstractNode roundLoser() {
			if (getCandidate() != null)
				return this;
			int[] votes = new int[children.size()];
			for (int i = 0; i < votes.length; i++) {
				AbstractNode child = children.get(i);
				votes[i] = child.getVotes();
			}
			return children.get(Utilities.min(votes));
		}
	}
}
