package boardGui;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
	}

}