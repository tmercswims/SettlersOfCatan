package edu.brown.cs032.eheimark.catan.trade;

import javax.swing.JLabel;

import edu.brown.cs032.eheimark.catan.menu.Constants;

public class TradeLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	public TradeLabel(String name) {
		super(name);

		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);

		setFont(Constants.DEFAULT_BUTTON_FONT);
		setHorizontalAlignment(JLabel.LEFT);
		setForeground(Constants.CATAN_RED);

	}
}