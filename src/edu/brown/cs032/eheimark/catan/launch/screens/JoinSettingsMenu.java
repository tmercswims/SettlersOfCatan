package edu.brown.cs032.eheimark.catan.launch.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.brown.cs032.eheimark.catan.launch.SettlersOfCatan;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanTextField;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.ShadowLabel;


/**
 * The Class JoinSettingsMenu is the launch menu page that allows the user to customize the settings for joining a game.
 */
public class JoinSettingsMenu extends CatanMenu {
	private static final long serialVersionUID = -4259785400207373901L;
	private final JButton submit, back; // buttons on screen
	private final JTextField portTF, usernameTF, hostnameTF; // fields where user can enter input
	private final JLabel joinLbl, usernameLbl, hostnameLbl; // labels
	private final SettlersOfCatan soc; // reference to instance

	/**
	 * Instantiates a new join settings menu.
	 * @param socIn reference to Settlers Of Catan class instance (which contains launch configurations etc)
	 */
	public JoinSettingsMenu(SettlersOfCatan socIn) {
		super();
		setButtonsPanelBorder(BorderFactory.createEmptyBorder(160, 0, 0, 0));

		this.soc = socIn;
		joinLbl = new ShadowLabel("Select Join Port:");
		portTF = new CatanTextField(soc.getLaunchConfiguration().getJoinPort());
		usernameLbl = new ShadowLabel("Select Username:");
		usernameTF = new CatanTextField(soc.getLaunchConfiguration().getName());
		hostnameLbl = new ShadowLabel("Select Hostname:");
		hostnameTF = new CatanTextField(soc.getLaunchConfiguration().getHostName());
		submit = new CatanMenuButton("Submit");
		back = new CatanMenuButton("Back");

		hostnameTF.getDocument().addDocumentListener(new DocumentListener() {
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
				try {
					String hn = hostnameTF.getText();
					soc.getLaunchConfiguration().setHostname(hn);
				}
				catch(NumberFormatException ex) {
					System.err.println(String.format("ERROR: %s", ex.getMessage()));
				}
			}
		});

		hostnameTF.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						hostnameTF.selectAll();
					}
				});
			}

			@Override
			public void focusLost(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						hostnameTF.select(0, 0);
					}
				});
			}
		});

		portTF.getDocument().addDocumentListener(new DocumentListener() {
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
				soc.getLaunchConfiguration().setJoinPort(portTF.getText());
			}
		});

		portTF.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						portTF.selectAll();
					}
				});
			}

			@Override
			public void focusLost(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						portTF.select(0, 0);
					}
				});
			}
		});

		usernameTF.getDocument().addDocumentListener(new DocumentListener() {
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
				soc.getLaunchConfiguration().setName(usernameTF.getText());
			}
		});

		usernameTF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						soc.getFrame().setPage(new JoinLoadingMenu(soc));
					}
				});
			}
		});

		usernameTF.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						usernameTF.selectAll();
					}
				});
			}

			@Override
			public void focusLost(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						usernameTF.select(0, 0);
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
						soc.getFrame().setPage(new JoinLoadingMenu(soc));
					}
				});
			}
		});

		addComponent(hostnameLbl);
		addComponent(hostnameTF);
		addComponent(joinLbl);
		addComponent(portTF);
		addComponent(usernameLbl);
		addComponent(usernameTF);
		addComponent(submit);
		addComponent(back);
	}

	/**
	 * Sets the focus for easy keyboard shortcuts.
	 */
	@Override
	public void requestFocus() {
		super.requestFocus();
		hostnameTF.requestFocus();
	}
}
