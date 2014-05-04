package edu.brown.cs032.atreil.catan.gui.trade;

import javax.swing.JFrame;

import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;

public class TradeFrameTest {

	public static void main(String[] args){
		
		
		TradeFrame frame = new TradeFrame("Trade!", new TradeMove("Thomas", "Alex", new int[] {-1,0,0,1,0}, 0), null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
}
