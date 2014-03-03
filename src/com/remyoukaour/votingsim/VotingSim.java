package com.remyoukaour.votingsim;

import javax.swing.UIManager;
import java.lang.Exception;

public class VotingSim {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception ex) {}
		VotingFrame gui = new VotingFrame("Voting Simulator");
		gui.setVisible(true);
	}
}
