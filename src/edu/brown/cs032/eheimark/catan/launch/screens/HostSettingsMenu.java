package edu.brown.cs032.eheimark.catan.launch.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.brown.cs032.eheimark.catan.launch.SettlersOfCatan;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.ShadowLabel;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanJRadioButton;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanTextField;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * The Class HostMenu is the launch menu page that allows the user to customize the settings for hosting a game.
 */
public class HostSettingsMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private final JButton submit, back; // Buttons
	private final JTextField port; // Select ports
	private final JRadioButton threePlayer, fourPlayer; // Indicates whether 3 or 4 player game
	private final ButtonGroup bg; // button group for three or four player selection
	private final JLabel hostLabel;
	private final SettlersOfCatan soc; // Reference to GUI

	/**
	 * Instantiates a new host settings menu.
	 * @param socIn reference to Settlers Of Catan class instance (which contains launch configurations etc)
	 */
	public HostSettingsMenu(SettlersOfCatan socIn) {
		super();
		this.soc = socIn;
		hostLabel = new ShadowLabel("Select Host Port:");
		port = new CatanTextField(soc.getLaunchConfiguration().getHostPort());
		submit = new CatanMenuButton("Submit");
		back = new CatanMenuButton("Back");

		bg = new ButtonGroup();
		threePlayer = new CatanJRadioButton("3 Player Game");
		fourPlayer = new CatanJRadioButton("4 Player Game");
		bg.add(threePlayer);
		bg.add(fourPlayer);
		if(soc.getLaunchConfiguration().isFourPlayerGame()) {
			fourPlayer.setSelected(true);
		}
		else {
			threePlayer.setSelected(true);
		}

		port.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				change();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				change();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				change();
			}
			private void change() {
				soc.getLaunchConfiguration().setHostPort(port.getText());
			}
		});

		port.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						port.selectAll();
					}
				});
			}

			@Override
			public void focusLost(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						port.select(0, 0);
					}
				});
			}
		});

		port.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						soc.getFrame().setPage(new HostLoadingMenu(soc));
					}
				});
			}
		});

		threePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						soc.getLaunchConfiguration().setFourPlayerGame(false);
					}
				});
			}
		});

		fourPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						soc.getLaunchConfiguration().setFourPlayerGame(true);
					}
				});
			}
		});

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						soc.getFrame().setPage(new MainMenu(soc));
					}
				});
			}
		});

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						soc.getFrame().setPage(new HostLoadingMenu(soc));
                        soc.getFrame().stopMusic();
					}
				});
			}
		});

		addComponent(hostLabel);
		addComponent(port);
		addComponent(threePlayer);
		addComponent(fourPlayer);
		addComponent(submit);
		addComponent(back);
	}
	
	/**
	 * Sets the focus for easy keyboard shortcuts.
	 */
	@Override
	public void requestFocus() {
		super.requestFocus();
		port.requestFocus();
	}
}
