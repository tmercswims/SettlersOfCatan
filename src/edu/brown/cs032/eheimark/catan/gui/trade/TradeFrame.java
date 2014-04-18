package edu.brown.cs032.eheimark.catan.gui.trade;
import edu.brown.cs032.eheimark.catan.menu.CatanFrame;

public class TradeFrame extends CatanFrame{
	private static final long serialVersionUID = 1L;

	public TradeFrame(String frameName, String playerName, Integer ore, Integer wheat, Integer wool, Integer lumber, Integer brick) {
		super(new TradePopup(playerName, ore, wheat, wool, lumber, brick), frameName);
	}

	public static void main(String[] args) {
		new TradeFrame("Trade Received!", "playerFrom", 1, 2, 3, 4, 5);
	}
}
