package edu.brown.cs032.eheimark.catan.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.brown.cs032.eheimark.catan.launch.SettlersOfCatan;

public class AlertFrame extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JPanel _myPanel;
	private final GUIFrame _gui;
	private final JButton _mainMenuButton;
	private final JLabel _myMessageLabel;
	private GridBagConstraints _gbc; // constraints

	public AlertFrame(String message, GUIFrame gui){
		super(gui, true);
		_gui = gui;
		_myPanel = new JPanel();
		getContentPane().add(_myPanel);
		
		_myMessageLabel = new JLabel(message);
		_myMessageLabel.setFont(Constants.ALERT_MENU_FONT);
		_myMessageLabel.setForeground(Constants.CATAN_ORANGE);

		_mainMenuButton = new JButton("Return to Main Menu");
		_mainMenuButton.setFont(Constants.ALERT_MENU_FONT);
		_mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		_mainMenuButton.addActionListener(this);
		_mainMenuButton.requestFocus();
		
		_myPanel.setLayout(new GridBagLayout());
		_gbc = new GridBagConstraints();
	    _gbc.gridx = _gbc.gridy = 0;
		_myPanel.add(_myMessageLabel, _gbc);
		_gbc.gridy++;
		_myPanel.add(_mainMenuButton, _gbc);
		_myPanel.setBackground(Constants.CATAN_RED);
		
		setUndecorated(true);
		setOpacity(0.75f);
		pack();
		setLocationRelativeTo(gui);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_gui.exit();
				new SettlersOfCatan();
				setVisible(false);
				dispose();
			}
		});
	}
}
