package com.remyoukaour.votingsim;

import javax.swing.*;

public class HTMLFrame extends JFrame {
	private static final long serialVersionUID = -2075129539344500686L;
	
	private final JEditorPane text = new JEditorPane();
	private final JScrollPane view = new JScrollPane(text);
	
	public HTMLFrame(String title, String content) {
		super(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
		setupText(content);
		setupView();
	}
	
	public void setupText(String content) {
		text.setContentType("text/html");
		text.setText(content);
		text.setCaretPosition(0);
		text.setEditable(false);
	}
	
	public void setupView() {
		view.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		view.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(view);
	}
}