package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.trade.Trade;
import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;

public class TabbedPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private final DrawingPanel _dp;

	public TabbedPanel(CatanClient client, DrawingPanel dp) {
		super();
		setLayout(new BorderLayout(0, 0));
		_dp = dp;
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel overview = new Overview(client);
		tabbedPane.addTab("Overview", null, overview, null);
		
		JPanel build = new Build(client, dp);
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
