package edu.brown.cs032.eheimark.catan.gui;

import javax.swing.JFrame;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class GUIFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public GUIFrame(CatanClient cc) {
		super("Settlers of Catan -- " + cc.getPlayerName());
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                   }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
		add(new GUI(cc));
		setVisible(true);
//		setResizable(false);
		setMinimumSize(Constants.GUI_SIZE);
//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}
}
