package edu.brown.cs032.sbreslow.catan.gui.board;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class boardGuiMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame("BOARD GUI TESTFRAME");
		f.add(new DrawingPanel());
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

}