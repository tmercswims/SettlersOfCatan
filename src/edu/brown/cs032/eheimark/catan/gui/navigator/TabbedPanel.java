package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.Update;
import edu.brown.cs032.eheimark.catan.gui.trade.Trade;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;

/**
 * The Class TabbedPanel is a JPanel that contains the tabs for navigating
 * the main game gui. This allows users to see the overview menu, trade menu, build menu,
 * and dev card menu contained within the tabbed panels.
 */
public class TabbedPanel extends JPanel implements Update {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The overview. */
	private final Overview overview;
	private final Trade trade;
	private final Build build;
	
	/** The devcard. */
	private final DevCard devcard;

	/**
	 * Instantiates a new tabbed panel.
	 *
	 * @param client the client
	 * @param dp the dp
	 */
	public TabbedPanel(CatanClient client, DrawingPanel dp) {
		super();
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setOpaque(true);
		add(tabbedPane, BorderLayout.CENTER);
		
		overview = new Overview(client);
		tabbedPane.addTab("Overview", null, overview, null);
		
		build = new Build(client, dp);
		tabbedPane.addTab("Build", null, build, null);
		
		trade = new Trade(client);
		tabbedPane.addTab("Trade", null, trade, null);
		
		devcard = new DevCard(client, dp);
		tabbedPane.addTab("Dev Card", null, devcard, null);
		
		setMinimumSize(Constants.TABBED_MENU_SIZE);
		setPreferredSize(Constants.TABBED_MENU_SIZE);
		setMaximumSize(Constants.TABBED_MENU_SIZE);
	}

	@Override
	public void ericUpdate() {
		overview.ericUpdate();
		trade.ericUpdate();
		build.ericUpdate();
	}
}
