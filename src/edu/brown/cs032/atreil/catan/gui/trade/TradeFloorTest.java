package edu.brown.cs032.atreil.catan.gui.trade;

import javax.swing.JFrame;

public class TradeFloorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new TradeFloor("Test", new int[] {0,0,0,0,0}));
		frame.setVisible(true);
		frame.pack();
	}

}
