package com.remyoukaour.votingsim;

import java.util.Vector;

public abstract class AbstractNode {
	private final Candidate candidate;
	private int votes;
	protected Vector<AbstractNode> children;
	
	protected AbstractNode(Candidate candidate, Vector<Candidate> ballot) {
		this.candidate = candidate;
		this.votes = 0;
		int size = ballot != null ? ballot.size() : 0;
		this.children = size > 0 ? new Vector<AbstractNode>() : null;
	}
	
	public Candidate getCandidate() {
		return candidate;
	}
	
	public int getVotes() {
		return votes;
	}
	
	public void count(double[] vote) {
		votes++;
		if (children == null)
			return;
		int winner = Utilities.min(vote);
		double[] subvote = Utilities.without(vote, winner);
		children.get(winner).count(subvote);
	}
	
	public AbstractNode roundWinner() {
		if (candidate != null)
			return this;
		int[] votes = new int[children.size()];
		for (int i = 0; i < votes.length; i++) {
			votes[i] = children.get(i).votes;
		}
		return children.get(Utilities.max(votes));
	}
	
	public abstract AbstractNode roundLoser();
	
	public Candidate winner() {
		AbstractNode winner = roundWinner();
		if (winner.votes * 2 > votes) {
			return winner.candidate;
		}
		AbstractNode loser = roundLoser();
		eliminate(loser.candidate);
		return winner();
	}
	
	public void eliminate(Candidate loser) {
		if (children == null)
			return;
		int size = children.size();
		for (int i = 0; i < size; i++) {
			AbstractNode child = children.get(i);
			if (child.candidate != loser) {
				child.eliminate(loser);
				continue;
			}
			children.remove(child);
			merge(child.children);
			size--;
			i--;
		}
	}
	
	public void merge(Vector<AbstractNode> orphans) {
		if (orphans == null)
			return;
		int size = children.size();
		for (AbstractNode orphan : orphans) {
			int i = 0;
			for (; i < size; i++)
				if (children.get(i).candidate == orphan.candidate)
					break;
			if (i < size) {
				AbstractNode child = children.get(i);
				child.votes += orphan.votes;
				child.merge(orphan.children);
			}
		}
	}
}
