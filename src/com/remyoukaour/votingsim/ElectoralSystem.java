package com.remyoukaour.votingsim;

import java.util.Vector;

public abstract class ElectoralSystem {
	private String name;
	
	public ElectoralSystem(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	public abstract Candidate elect(Populace populace, Vector<Candidate> ballot);
}
