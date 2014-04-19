package edu.brown.cs032.eheimark.catan.gui.navigator;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;

import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.trade.Trade;

//TODO: Switch to extending JTabbedPane from JFrame
public class TabbedPane extends JFrame{
	private static final long serialVersionUID = 1L;

	public TabbedPane() {
		super();
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel overview = new Overview();
		tabbedPane.addTab("Overview", null, overview, null);
		
		JPanel build = new Build();
		tabbedPane.addTab("Build", null, build, null);
		
		JPanel trade = new Trade();
		tabbedPane.addTab("Trade", null, trade, null);
		
		JPanel devcard = new JPanel();
		tabbedPane.addTab("Dev Card", null, devcard, null);
		
		setVisible(true);
		setResizable(false);
		setMinimumSize(Constants.MENU_SIZE);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new TabbedPane();
	}
}
