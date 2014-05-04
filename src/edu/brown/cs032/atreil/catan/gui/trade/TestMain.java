package edu.brown.cs032.atreil.catan.gui.trade;

import javax.swing.JFrame;


public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Trade trade = new Trade(null);
		trade.setResourceCount(new int[] {0,10,10,10,10});
		frame.add(trade);
		frame.setVisible(true);
		frame.pack();
	}

}
