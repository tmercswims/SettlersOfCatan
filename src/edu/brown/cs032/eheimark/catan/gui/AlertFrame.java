package edu.brown.cs032.eheimark.catan.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import edu.brown.cs032.eheimark.catan.launch.SettlersOfCatan;

public class AlertFrame extends JFrame {
	
	private GUIFrame _gui;
	
	public AlertFrame(String message, GUIFrame gui){
		super("ALERT!!!");
		_gui = gui;
		JPanel panel = new JPanel();
		JLabel label = new JLabel(message);
		label.setAlignmentX(SwingConstants.CENTER);
		panel.add(label);
		JButton menu = new JButton("Return to Main Menu");
		menu.setAlignmentX(SwingConstants.CENTER);
		menu.addActionListener(new MenuList(this));
		panel.add(menu);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.add(panel);
		this.setVisible(true);
		this.pack();
	}
	
	private class MenuList implements ActionListener {
		
		private JFrame _frame;
		
		public MenuList(JFrame frame){
			_frame  = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					_gui.exit();
					new SettlersOfCatan();
				}
			});
			_frame.setVisible(false);
			_frame.dispose();
		}
		
	}

}
