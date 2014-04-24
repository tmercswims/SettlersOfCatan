package edu.brown.cs032.eheimark.catan.gui.trade;

import javax.swing.JFrame;
import edu.brown.cs032.eheimark.catan.gui.Constants;

public class TradeFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	public TradeFrame(String frameName, String playerName, int[] trade) {
		super(frameName);

		setMinimumSize(Constants.TABBED_MENU_SIZE);
		add(new TradePopup(playerName, trade));
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
	}
}
