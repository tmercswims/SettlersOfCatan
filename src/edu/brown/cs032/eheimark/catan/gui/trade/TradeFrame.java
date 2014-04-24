package edu.brown.cs032.eheimark.catan.gui.trade;

import javax.swing.JFrame;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;

public class TradeFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	public TradeFrame(String frameName, TradeMove trade, CatanClient client) {
		super(frameName);
		setMinimumSize(Constants.TABBED_MENU_SIZE);
		add(new TradePopup(client, trade));
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
	}
}
