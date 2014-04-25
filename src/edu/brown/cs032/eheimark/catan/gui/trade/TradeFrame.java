package edu.brown.cs032.eheimark.catan.gui.trade;

import javax.swing.JFrame;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;

/**
 * The Class TradeFrame is a frame for the Trade Proposal JPanel. A trade proposal
 * shows up in its own frame, and then players can accept or reject a trade.
 */
public class TradeFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new trade frame.
	 *
	 * @param frameName the frame name
	 * @param trade the trade
	 * @param client the client
	 */
	public TradeFrame(String frameName, TradeMove trade, CatanClient client) {
		super(frameName);
		setMinimumSize(Constants.TABBED_MENU_SIZE);
		add(new OutstandingTrade(client, trade, this));
		pack();
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Close the frame and dispose properly.
	 */
	public void close() {
		setVisible(false);
		dispose();
	}
}
