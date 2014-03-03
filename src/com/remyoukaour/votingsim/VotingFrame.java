package com.remyoukaour.votingsim;

import com.remyoukaour.votingsim.votingsystems.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Vector;
import java.util.Enumeration;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;

public class VotingFrame extends JFrame implements ActionListener,
		ListSelectionListener, ComponentListener, MouseListener {
	private static final long serialVersionUID = -6021358028297095718L;
	
	private final JMenuBar menuBar = new JMenuBar();
	
	private final JMenu electionMenu = new JMenu("Election");
	private final JMenuItem newElectionItem = new JMenuItem("New", KeyEvent.VK_N);
	private final JMenuItem loadElectionItem = new JMenuItem("Load...", KeyEvent.VK_L);
	private final JMenuItem saveElectionItem = new JMenuItem("Save...", KeyEvent.VK_S);
	private final JMenuItem startElectionItem = new JMenuItem("Start", KeyEvent.VK_R);
	private final JMenuItem pauseElectionItem = new JMenuItem("Pause", KeyEvent.VK_P);
	private final JMenuItem stopElectionItem = new JMenuItem("Stop", KeyEvent.VK_T);
	
	private final JMenu imageMenu = new JMenu("Image");
	private final JMenuItem clearImageItem = new JMenuItem("Clear", KeyEvent.VK_C);
	private final JMenuItem saveImageItem = new JMenuItem("Save...", KeyEvent.VK_S);
	
	private final JMenu ballotMenu = new JMenu("Ballot");
	private final JMenuItem addBallotItem = new JMenuItem("Add", KeyEvent.VK_A);
	private final JMenuItem clearBallotItem = new JMenuItem("Clear", KeyEvent.VK_C);
	
	private final JMenu helpMenu = new JMenu("Help");
	private final JMenuItem helpItem = new JMenuItem("Help", KeyEvent.VK_H);
	private final JMenuItem aboutItem = new JMenuItem("About", KeyEvent.VK_A);
	
	private final JPanel contentPanel = new JPanel();
	
	private final JPanel populacePanel = new JPanel();
	private final ImagePanel image = new ImagePanel(Color.WHITE, -0.25, 1.25, -0.25, 1.25);
	private final Border imageBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED,
			Color.WHITE, new Color(122, 138, 153));
	
	private final JPanel populaceControls = new JPanel();
	private final Vector<ElectoralSystem> systems = new Vector<ElectoralSystem>();
	private final JLabel systemLabel = new JLabel("Electoral system:");
	private final JComboBox<ElectoralSystem> systemBox = new JComboBox<ElectoralSystem>(systems);
	private final JLabel sizeLabel = new JLabel("Population:");
	private final JTextField size = new JTextField("1000", 7);
	private final JLabel varianceLabel = new JLabel("Variance:");
	private final JTextField variance = new JTextField("0.5", 4);
	private final JFileChooser imageChooser = new JFileChooser();
	private final FileExtensionFilter imageFilter = new FileExtensionFilter("Image files",
			"png");
	
	private final JPanel ballotPanel = new JPanel();
	private final DefaultListModel<Candidate> ballot = new DefaultListModel<Candidate>();
	private final JList<Candidate> ballotList = new JList<Candidate>(ballot);
	private final JScrollPane ballotScroll = new JScrollPane(ballotList);
	private final JButton add = new JButton("Add");
	private final JButton remove = new JButton("Remove");
	private final JButton update = new JButton("Update");
	private final JButton start = new JButton("Start");
	private final JButton stop = new JButton("Stop");
	private ElectionTask electionTask = null;
	private Dimension pauseState = null;
	private final JFileChooser electionChooser = new JFileChooser();
	private final FileExtensionFilter electionFilter =
			new FileExtensionFilter("Election parameters", "election");
	
	private final JPanel ballotControls = new JPanel();
	private final JLabel colorLabel = new JLabel("Color:");
	private final JTextField color = new JTextField("000000", 6);
	private final JLabel locationLabel = new JLabel("Location:");
	private final JTextField locationX = new JTextField("0.0", 4);
	private final JTextField locationY = new JTextField("0.0", 4);
	
	public VotingFrame(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		imageChooser.setAcceptAllFileFilterUsed(false);
		imageChooser.addChoosableFileFilter(imageFilter);
		electionChooser.setAcceptAllFileFilterUsed(false);
		electionChooser.addChoosableFileFilter(electionFilter);
		// Setup
		setupMenuBar();
		setupElectionMenu();
		setupImageMenu();
		setupBallotMenu();
		setupHelpMenu();
		setupContent();
		setupPopulace();
		setupPopulaceControls();
		setupBallot();
		setupBallotControls();
		// Add listeners
		addComponentListener(this);
		// Show
		newElection();
		pack();
		setMinimumSize(getPreferredSize());
		ballotScroll.grabFocus();
	}
	
	public void setupMenuBar() {
		// Add components
		setJMenuBar(menuBar);
		menuBar.add(electionMenu);
		menuBar.add(imageMenu);
		menuBar.add(ballotMenu);
		menuBar.add(helpMenu);
	}
	
	public void setupElectionMenu() {
		electionMenu.setMnemonic(KeyEvent.VK_E);
		newElectionItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		loadElectionItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.CTRL_MASK));
		saveElectionItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK|ActionEvent.SHIFT_MASK));
		startElectionItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.CTRL_MASK));
		pauseElectionItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		stopElectionItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				ActionEvent.CTRL_MASK));
		// Add listeners
		newElectionItem.addActionListener(this);
		saveElectionItem.addActionListener(this);
		loadElectionItem.addActionListener(this);
		startElectionItem.addActionListener(this);
		pauseElectionItem.addActionListener(this);
		stopElectionItem.addActionListener(this);
		// Add components
		electionMenu.add(newElectionItem);
		electionMenu.add(loadElectionItem);
		electionMenu.add(saveElectionItem);
		electionMenu.addSeparator();
		electionMenu.add(startElectionItem);
		electionMenu.add(pauseElectionItem);
		electionMenu.add(stopElectionItem);
	}
	
	public void setupImageMenu() {
		imageMenu.setMnemonic(KeyEvent.VK_I);
		clearImageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));
		saveImageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		// Add listeners
		clearImageItem.addActionListener(this);
		saveImageItem.addActionListener(this);
		// Add components
		imageMenu.add(clearImageItem);
		imageMenu.add(saveImageItem);
	}
	
	public void setupBallotMenu() {
		ballotMenu.setMnemonic(KeyEvent.VK_B);
		addBallotItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.CTRL_MASK));
		clearBallotItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK|ActionEvent.SHIFT_MASK));
		// Add listeners
		addBallotItem.addActionListener(this);
		clearBallotItem.addActionListener(this);
		// Add components
		ballotMenu.add(addBallotItem);
		ballotMenu.add(clearBallotItem);
	}
	
	public void setupHelpMenu() {
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));
		// Add listeners
		helpItem.addActionListener(this);
		aboutItem.addActionListener(this);
		// Add components
		helpMenu.add(helpItem);
		helpMenu.add(aboutItem);
	}
	
	public void setupContent() {
		// Add components
		add(contentPanel);
		contentPanel.add(populacePanel);
		contentPanel.add(ballotPanel);
		// Layout
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		contentPanel.setLayout(layout);
		contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, 5);
		layout.setConstraints(populacePanel, constraints);
		constraints.gridx = 1;
		constraints.weightx = 0.0;
		constraints.insets = new Insets(0, 0, 0, 0);
		layout.setConstraints(ballotPanel, constraints);
	}
	
	public void setupPopulace() {
		image.setBorder(imageBorder);
		image.setHeight(200);
		// Add listeners
		image.addMouseListener(this);
		// Add components
		populacePanel.add(image);
		populacePanel.add(populaceControls);
		// Layout
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		populacePanel.setLayout(layout);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 5, 0);
		layout.setConstraints(image, constraints);
		constraints.gridy = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.PAGE_END;
		constraints.insets = new Insets(0, 0, 0, 0);
		layout.setConstraints(populaceControls, constraints);
	}
	
	public void setupPopulaceControls() {
		systems.addElement(new Plurality());
		systems.addElement(new AntiPlurality());
		systems.addElement(new Dabagh());
		systems.addElement(new ForAndAgainst());
		systems.addElement(new Bucklin());
		systems.addElement(new MajorityJudgment());
		systems.addElement(new Approval(0.0, 0.25));
		systems.addElement(new StrategicApproval());
		systems.addElement(new Borda());
		systems.addElement(new Nauru());
		systems.addElement(new InstantRunoff());
		systems.addElement(new Coombs());
		systems.addElement(new Contingent());
		systems.addElement(new Concordet());
		systems.addElement(new Range());
		systems.addElement(new RandomDictator());
		systems.addElement(new RandomPair());
		systems.addElement(new Sortition());
		systems.addElement(new Optimal());
		systems.addElement(new Pessimal());
		systemBox.setSelectedIndex(0);
		size.setToolTipText("Positive integer");
		variance.setToolTipText("Between 0 and 1");
		// Add components
		populaceControls.add(systemLabel);
		populaceControls.add(systemBox);
		populaceControls.add(sizeLabel);
		populaceControls.add(size);
		populaceControls.add(varianceLabel);
		populaceControls.add(variance);
		// Layout
		GroupLayout layout = new GroupLayout(populaceControls);
		populaceControls.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(systemLabel)
				.addGap(5)
				.addComponent(sizeLabel)
				.addGap(5)
				.addComponent(varianceLabel)
			)
			.addGap(5)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(systemBox)
				.addGap(5)
				.addComponent(size)
				.addGap(5)
				.addComponent(variance)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(systemLabel)
				.addGap(5)
				.addComponent(systemBox)
			)
			.addGap(5)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(sizeLabel)
				.addGap(5)
				.addComponent(size)
			)
			.addGap(5)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(varianceLabel)
				.addGap(5)
				.addComponent(variance)
			)
		);
	}
	
	public void setupBallot() {
		ballotList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ballotScroll.setPreferredSize(new Dimension(0,
				(int)image.getPreferredSize().getHeight()-1));
		clearBallot();
		// Add listeners
		ballotList.addListSelectionListener(this);
		add.addActionListener(this);
		remove.addActionListener(this);
		update.addActionListener(this);
		start.addActionListener(this);
		stop.addActionListener(this);
		// Add components
		ballotPanel.add(ballotScroll);
		ballotPanel.add(ballotControls);
		ballotPanel.add(add);
		ballotPanel.add(remove);
		ballotPanel.add(update);
		ballotPanel.add(start);
		ballotPanel.add(stop);
		// Layout
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		ballotPanel.setLayout(layout);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 5, 0);
		layout.setConstraints(ballotScroll, constraints);
		constraints.gridy = 1;
		constraints.weighty = 0.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(ballotControls, constraints);
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.weightx = 0.5;
		constraints.insets = new Insets(0, 0, 5, 5);
		layout.setConstraints(add, constraints);
		constraints.gridx = 1;
		constraints.weightx = 0.0;
		layout.setConstraints(remove, constraints);
		constraints.gridx = 2;
		constraints.insets = new Insets(0, 0, 5, 0);
		layout.setConstraints(update, constraints);
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.weightx = 0.5;
		constraints.insets = new Insets(0, 0, 0, 5);
		layout.setConstraints(start, constraints);
		constraints.gridx = 2;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.insets = new Insets(0, 0, 0, 0);
		layout.setConstraints(stop, constraints);
	}
	
	public void setupBallotControls() {
		color.setToolTipText("Hexadecimal RGB triple");
		locationX.setToolTipText("Between 0 and 1");
		locationY.setToolTipText("Between 0 and 1");
		// Add components
		ballotControls.add(colorLabel);
		ballotControls.add(color);
		ballotControls.add(locationLabel);
		ballotControls.add(locationX);
		ballotControls.add(locationY);
		// Layout
		GroupLayout layout = new GroupLayout(ballotControls);
		ballotControls.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(colorLabel)
				.addGap(5)
				.addComponent(locationLabel)
			)
			.addGap(5)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(color)
				.addGap(5)
				.addGroup(layout.createSequentialGroup()
					.addComponent(locationX)
					.addGap(5)
					.addComponent(locationY)
				)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(colorLabel)
				.addGap(5)
				.addComponent(color)
			)
			.addGap(5)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(locationLabel)
				.addGap(5)
				.addComponent(locationX)
				.addGap(5)
				.addComponent(locationY)
			)
		);
	}
	
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == clearImageItem)
			clearImage();
		else if (source == saveImageItem)
			saveImage();
		else if (source == newElectionItem)
			newElection();
		else if (source == loadElectionItem)
			loadElection();
		else if (source == saveElectionItem)
			saveElection();
		else if (source == startElectionItem)
			startElections();
		else if (source == pauseElectionItem)
			stopElections();
		else if (source == stopElectionItem || source == stop)
			stopElections(true);
		else if (source == addBallotItem || source == add)
			addCandidate();
		else if (source == clearBallotItem)
			clearBallot();
		else if (source == helpItem)
			showHelp();
		else if (source == aboutItem)
			showAbout();
		else if (source == remove)
			removeCandidate(ballotList.getSelectedIndex());
		else if (source == update)
			updateCandidate(ballotList.getSelectedIndex());
		else if (source == start) {
			String cmd = event.getActionCommand();
			if (cmd.equals("Start"))
				startElections();
			else if (cmd.equals("Pause"))
				stopElections();
		}
	}
	
	public void valueChanged(ListSelectionEvent event) {
		if (event.getValueIsAdjusting())
			return;
		if (ballotList.getSelectedIndex() == -1)
			deselectCandidate();
		else
			selectCandidate(ballotList.getSelectedIndex());
	}
	
	public void componentResized(ComponentEvent event) {
		int oldWidth = image.getPreferredSize().width;
		int oldHeight = image.getPreferredSize().height;
		int width = image.getSize().width;
		int height = image.getSize().height;
		if (Math.abs(width-oldWidth) > Math.abs(height-oldHeight))
			image.setWidth(width);
		else
			image.setHeight(height);
		pack();
	}
	
	public void componentMoved(ComponentEvent event) {}
	public void componentHidden(ComponentEvent event) {}
	public void componentShown(ComponentEvent event) {}
	
	public void mouseClicked(MouseEvent event) {
		Point2D.Double point = image.getPoint(event.getX(), event.getY());
		Color color = Utilities.randomColor();
		addCandidate(point.getX(), point.getY(), color);
	}
	
	public void mousePressed(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	
	public void newElection() {
		stopElections(true);
		clearImage();
		clearBallot();
		size.setText("1000");
		variance.setText("0.5");
		color.setText("000000");
		locationX.setText("0.0");
		locationY.setText("0.0");
	}
	
	public void loadElection() {
		int status = electionChooser.showOpenDialog(populacePanel);
		if (status != JFileChooser.APPROVE_OPTION)
			return;
		try {
			clearBallot();
			String filename = electionChooser.getSelectedFile().getAbsolutePath();
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String[] data = reader.readLine().split(" ");
			reader.close();
			if (!data[0].equals("ELECTION") || (data.length - 7) % 3 != 0)
				throw new IOException();
			systemBox.setSelectedIndex(Integer.parseInt(data[1]));
			size.setText(String.valueOf(Integer.parseInt(data[2])));
			variance.setText(String.valueOf(Double.parseDouble(data[3])));
			color.setText(Utilities.colorToString(Utilities.stringToColor(data[4])));
			locationX.setText(String.valueOf(Double.parseDouble(data[5])));
			locationY.setText(String.valueOf(Double.parseDouble(data[6])));
			for (int i = 7; i < data.length; i += 3) {
				Color c = Utilities.stringToColor(data[i]);
				double x = Double.parseDouble(data[i+1]);
				double y = Double.parseDouble(data[i+2]);
				addCandidate(x, y, c);
			}
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Could not load file!",
					"Error", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public void saveElection() {
		int status = electionChooser.showSaveDialog(populacePanel);
		if (status != JFileChooser.APPROVE_OPTION)
			return;
		try {
			String filename = electionChooser.getSelectedFile().getAbsolutePath();
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write("ELECTION ");
			writer.write(String.valueOf(systemBox.getSelectedIndex()) + " ");
			writer.write(String.valueOf(Integer.parseInt(size.getText())) + " ");
			writer.write(String.valueOf(Double.parseDouble(variance.getText())) + " ");
			writer.write(Utilities.colorToString(Utilities.stringToColor(color.getText())) + " ");
			writer.write(String.valueOf(Double.parseDouble(locationX.getText())) + " ");
			writer.write(String.valueOf(Double.parseDouble(locationY.getText())));
			for (Enumeration<?> e = ballot.elements(); e.hasMoreElements();) {
				Candidate c = (Candidate)e.nextElement();
				writer.write(" " + Utilities.colorToString(c.getColor()) + " ");
				writer.write(String.valueOf(c.getX()) + " ");
				writer.write(String.valueOf(c.getY()));
			}
			writer.flush();
			writer.close();
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Could not save file!",
					"Error", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public void showHelp() {
		InputStream stream = getClass().getResourceAsStream("/res/help.html");
		String text = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String newline = System.getProperty("line.separator");
			StringBuffer buffer = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line + newline);
			}
			text = buffer.toString();
		}
		catch (Exception ex) {
			text = "<html><span style='color: #FF0000;'>Could not read help file!</span></html>";
		}
		HTMLFrame helpFrame = new HTMLFrame("Help", text);
		helpFrame.setSize(500, 500);
		helpFrame.setVisible(true);
	}
	
	public void showAbout() {
		String aboutText = "<html><body>" +
				"<b>Voting Simulator</b><br><br>" +
				"Copyright &copy; 2011 Remy Oukaour<br><br>" +
				"Inspired by Ka-Ping Yee's visualizations and by<br>" +
				"Warren D. Smith's Infinitely Extendible Voting Simulator" +
				"</body></html>";
		JOptionPane.showMessageDialog(this, aboutText, "About",
				JOptionPane.PLAIN_MESSAGE);
	}
	
	public void saveImage() {
		int status = imageChooser.showSaveDialog(populacePanel);
		if (status != JFileChooser.APPROVE_OPTION)
			return;
		try {
			File file = imageChooser.getSelectedFile();
			ImageIO.write(image.getImage(), "png", file);
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Could not save file!",
					"Error", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public void clearImage() {
		image.clearImage();
		markCandidates();
	}
	
	public void clearBallot() {
		ballot.clear();
		remove.setEnabled(false);
		update.setEnabled(false);
		startElectionItem.setEnabled(false);
		if (start.getText() == "Start")
			start.setEnabled(false);
	}
	
	public void deselectCandidate() {
		remove.setEnabled(false);
		update.setEnabled(false);
	}
	
	public void selectCandidate(int i) {
		remove.setEnabled(true);
		update.setEnabled(true);
		Candidate c = (Candidate)ballot.elementAt(i);
		locationX.setText(String.format("%.2f", c.getX()));
		locationY.setText(String.format("%.2f", c.getY()));
		color.setText(Utilities.colorToString(c.getColor()));
	}
	
	public void addCandidate() {
		double x = Double.parseDouble(locationX.getText());
		double y = Double.parseDouble(locationY.getText());
		Color c = Utilities.stringToColor(color.getText());
		addCandidate(x, y, c);
	}
	
	public void addCandidate(double x, double y, Color c) {
		ballot.addElement(new Candidate(x, y, c));
		start.setEnabled(true);
		startElectionItem.setEnabled(true);
		if (electionTask == null)
			markCandidates();
	}
	
	public void removeCandidate(int i) {
		ballot.remove(i);
		if (electionTask == null)
			markCandidates();
		if (ballot.isEmpty())
			clearBallot();
	}
	
	public void updateCandidate(int i) {
		double x = Double.parseDouble(locationX.getText());
		double y = Double.parseDouble(locationY.getText());
		Color c = Utilities.stringToColor(color.getText());
		ballot.remove(i);
		ballot.insertElementAt(new Candidate(x, y, c), i);
		if (electionTask == null)
			markCandidates();
	}
	
	public void markCandidates() {
		for (Enumeration<?> e = ballot.elements(); e.hasMoreElements();) {
			Candidate c = (Candidate)e.nextElement();
			image.putMark(c.getX(), c.getY(), c.getColor());
		}
	}
	
	public void startElections() {
		if (pauseState == null)
			stopElections(true);
		electionTask = new ElectionTask(pauseState);
		electionTask.execute();
		startElectionItem.setEnabled(false);
		pauseElectionItem.setEnabled(true);
		stopElectionItem.setEnabled(true);
		stop.setEnabled(true);
		start.setText("Pause");
		pauseState = null;
	}
	
	public void stopElections() {
		stopElections(false);
	}
	
	public void stopElections(boolean cancel) {
		if (electionTask != null)
			electionTask.cancel(true);
		electionTask = null;
		startElectionItem.setEnabled(!ballot.isEmpty());
		pauseElectionItem.setEnabled(false);
		start.setText("Start");
		start.setEnabled(!ballot.isEmpty());
		if (cancel) {
			clearImage();
			pauseState = null;
			stopElectionItem.setEnabled(false);
			stop.setEnabled(false);
		}
		markCandidates();
	}
	
	protected class ElectionTask extends SwingWorker<Void, Void> {
		protected int i, j, popSize, width, height;
		protected double stdev;
		protected ElectoralSystem system;
		protected Vector<Candidate> ballotVector;
		
		public ElectionTask() {
			this(null);
		}
		
		public ElectionTask(Dimension d) {
			i = d == null ? 0 : (int)d.getHeight();
			j = d == null ? 0 : (int)d.getWidth();
			popSize = Integer.parseInt(size.getText());
			stdev = Double.parseDouble(variance.getText());
			width = image.getWidth();
			height = image.getHeight();
			system = (ElectoralSystem)systemBox.getSelectedItem();
			ballotVector = new Vector<Candidate>();
			for (Enumeration<?> e = ballot.elements(); e.hasMoreElements();) {
				ballotVector.addElement((Candidate)e.nextElement());
			}
		}
		
		public void elect() {
			Point2D.Double center = image.getPoint(i, j);
			Populace populace = new Populace(popSize, center, stdev);
			Candidate winner = system.elect(populace, ballotVector);
			image.putPoint(i, j, winner.getColor());
		}
		
		@Override
		public Void doInBackground() {
			for (; !isCancelled() && i < height; i++) {
				for (; !isCancelled() && j < width; j++) {
					elect();
				}
				if (!isCancelled()) {
					j = 0;
				}
			}
			if (isCancelled()) {
				pauseState = new Dimension(j, i);
			}
			else {
				markCandidates();
			}
			return null;
		}
		
		@Override
		public void done() {
			stopElections();
		}
	}
}
