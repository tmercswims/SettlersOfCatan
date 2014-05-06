package edu.brown.cs032.atreil.catan.gui.trade;

import javax.swing.JFrame;

public class ResourceArrayTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ResourceArray(new int[]{1,2,3,4,5}));
		frame.setVisible(true);
		frame.pack();
	}

}
