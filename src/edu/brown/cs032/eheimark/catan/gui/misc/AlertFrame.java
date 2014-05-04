package edu.brown.cs032.eheimark.catan.gui.misc;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_ORANGE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_RED;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Fonts.ALERT_MENU_FONT;

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

import edu.brown.cs032.eheimark.catan.gui.GUIFrame;
import edu.brown.cs032.eheimark.catan.launch.SettlersOfCatan;

/**
 * The Class AlertFrame is used for alerts indicating sudden disconnection or
 * that the server has dropped
 */
public class AlertFrame extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JPanel _myPanel;
	private final GUIFrame _gui;
	private final JButton _mainMenuButton;
	private final JLabel _myMessageLabel;
	private GridBagConstraints _gbc; // constraints

	/**
	 * Instantiates a new alert frame.
	 *
	 * @param message the message
	 * @param gui the gui
	 */
	public AlertFrame(String message, GUIFrame gui){
		super(gui, true);
		_gui = gui;
		_myPanel = new JPanel();
		getContentPane().add(_myPanel);

		_myMessageLabel = new JLabel(message);
		_myMessageLabel.setFont(ALERT_MENU_FONT);
		_myMessageLabel.setForeground(CATAN_ORANGE);

		_mainMenuButton = new JButton("Return to Main Menu");
		_mainMenuButton.setFont(ALERT_MENU_FONT);
		_mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		_mainMenuButton.addActionListener(this);
		_mainMenuButton.requestFocus();

		_myPanel.setLayout(new GridBagLayout());
		_gbc = new GridBagConstraints();
		_gbc.gridx = _gbc.gridy = 0;
		_myPanel.add(_myMessageLabel, _gbc);
		_gbc.gridy++;
		_myPanel.add(_mainMenuButton, _gbc);
		_myPanel.setBackground(CATAN_RED);

		setUndecorated(true);
		setOpacity(0.75f);
		pack();
		setLocationRelativeTo(gui);
		setVisible(true);
	}

	/**
	 * Alert menu action listener pops up new main menu.
	 */
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
