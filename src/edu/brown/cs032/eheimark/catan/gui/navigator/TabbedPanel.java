package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.trade.Trade;

public class TabbedPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public TabbedPanel(CatanClient client) {
		super();
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel overview = new Overview(client);
		tabbedPane.addTab("Overview", null, overview, null);
		
		JPanel build = new Build();
		tabbedPane.addTab("Build", null, build, null);
		
		JPanel trade = new Trade();
		tabbedPane.addTab("Trade", null, trade, null);
		
		JPanel devcard = new JPanel();
		tabbedPane.addTab("Dev Card", null, devcard, null);
		
		setMinimumSize(Constants.TABBED_MENU_SIZE);
		setPreferredSize(Constants.TABBED_MENU_SIZE);
		setMaximumSize(Constants.TABBED_MENU_SIZE);
	}
	
//	public static void main(String[] args) {
//		new TabbedPanel();
//	}
}
