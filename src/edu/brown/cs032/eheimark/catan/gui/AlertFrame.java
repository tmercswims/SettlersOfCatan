package edu.brown.cs032.eheimark.catan.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import edu.brown.cs032.eheimark.catan.launch.SettlersOfCatan;
import edu.brown.cs032.sbreslow.catan.gui.devCards.BackgroundPanel;

public class AlertFrame extends JFrame {
	
	private GUIFrame _gui;
	
	public AlertFrame(String message, GUIFrame gui){
		super("ALERT!!!");
		_gui = gui;
		JPanel panel = new BackgroundPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel(message);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setForeground(Constants.CATAN_RED);
		label.setFont(Constants.ALERT_MENU_FONT);
		panel.add(label);
		JButton menu = new JButton("Return to Main Menu");
		menu.setAlignmentX(Component.CENTER_ALIGNMENT);
		menu.addActionListener(new MenuList(this));
		panel.add(menu);
		this.setLocationRelativeTo(gui);
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
