package com.remyoukaour.votingsim.votingsystems;

import com.remyoukaour.votingsim.*;
import java.util.Vector;

public class Coombs extends ElectoralSystem {
	public Coombs() {
		super("Coombs");
	}
	
	public Candidate elect(Populace populace, Vector<Candidate> ballot) {
		CoombsNode votes = new CoombsNode(ballot);
		int n = populace.getSize();
		for (int i = 0; i < n; i++) {
			Voter voter = populace.getRandomVoter();
			double[] vote = voter.vote(ballot);
			votes.count(vote);
		}
		return votes.winner();
	}
	
	protected class CoombsNode extends AbstractNode {
		public CoombsNode(Vector<Candidate> ballot) {
			this(null, ballot);
		}
		
		protected CoombsNode(Candidate candidate, Vector<Candidate> ballot) {
			super(candidate, ballot);
			int size = ballot.size();
			for (int i = 0; i < size; i++) {
				Vector<Candidate> subballot = new Vector<Candidate>(ballot);
				Candidate child = subballot.remove(i);
				children.addElement(new CoombsNode(child, subballot));
			}
		}
		
		public AbstractNode roundLoser() {
			if (children == null || children.size() == 0)
				return this;
			Vector<AbstractNode> losers = new Vector<AbstractNode>();
			for (AbstractNode child : children)
				losers.addElement(child.roundLoser());
			int[] votes = new int[losers.size()];
			for (int i = 0; i < votes.length; i++) {
				AbstractNode loser = losers.get(i);
				votes[i] = loser.getVotes();
			}
			return losers.get(Utilities.min(votes));
		}
	}
}
