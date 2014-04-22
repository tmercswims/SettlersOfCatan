package edu.brown.cs032.eheimark.catan.launch.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.brown.cs032.eheimark.catan.launch.SettlersOfCatan;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanJLabel;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanTextField;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


/**
 * The Class JoinSettingsMenu is the launch menu page that allows the user to customize the settings for joining a game.
 */
public class JoinSettingsMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private final JButton submit, back;
	private final JTextField portTF, usernameTF, hostnameTF;
	private final JLabel joinLbl, usernameLbl, hostnameLbl;
	private final SettlersOfCatan soc;

	/**
	 * Instantiates a new join settings menu.
	 * @param soc reference to Settlers Of Catan class instance (which contains launch configurations etc)
	 */
	public JoinSettingsMenu(SettlersOfCatan socIn) {
		super();
		this.soc = socIn;
		joinLbl = new CatanJLabel("Select Join Port:");
		portTF = new CatanTextField(Integer.toString(soc.getLaunchConfiguration().getJoinPort()));
		usernameLbl = new CatanJLabel("Select Username:");
		usernameTF = new CatanTextField(soc.getLaunchConfiguration().getName());
		hostnameLbl = new CatanJLabel("Select Hostname:");
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
				catch(NumberFormatException e1) {
					e1.printStackTrace();
				}
			}
		});

		hostnameTF.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				hostnameTF.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				hostnameTF.select(0, 0);
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
				try {
					int joinPort = Integer.parseInt(portTF.getText());
					soc.getLaunchConfiguration().setJoinPort(joinPort);
				}
				catch(NumberFormatException e1) {
					e1.printStackTrace();
				}
			}
		});

		portTF.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				portTF.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				portTF.select(0, 0);
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
				usernameTF.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				usernameTF.select(0, 0);
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
}
