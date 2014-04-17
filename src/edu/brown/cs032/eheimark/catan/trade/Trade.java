package edu.brown.cs032.eheimark.catan.trade;

import edu.brown.cs032.eheimark.catan.menu.CatanFrame;

public class Trade {
	public static CatanFrame frame;
	
	public Trade() {
		frame = new CatanFrame(new TradeMenu(), "Trade Menu");
	}
	
	public static void main(String[] args) {
		new Trade();
	}
}
