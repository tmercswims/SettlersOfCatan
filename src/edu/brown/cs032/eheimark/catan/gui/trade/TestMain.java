package edu.brown.cs032.eheimark.catan.gui.trade;

import javax.swing.JFrame;

public class TestMain {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Trade(null));
		frame.setVisible(true);
		frame.pack();
	}

}
