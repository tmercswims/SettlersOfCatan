package edu.brown.cs032.eheimark.catan.gui;

import javax.swing.JFrame;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The Class GUIFrame contains the main gui panel with the board at top
 * and tabbed panels at the bottom.
 */
public class GUIFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new GUI frame.
	 *
	 * @param cc the cc
	 */
	public GUIFrame(CatanClient cc) {
		super("Settlers of Catan : " + cc.getPlayerName());
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                   }
            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
		add(new GUI(cc));
		setVisible(true);
		setResizable(false);
		setMinimumSize(Constants.GUI_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}
}
