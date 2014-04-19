package edu.brown.cs032.eheimark.catan.menu.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.brown.cs032.eheimark.catan.jcomponents.CatanJLabel;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanTextField;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;


public class JoinGameMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private final JButton submit, back;
	private final JTextField portTF, usernameTF, hostnameTF;
	private final JLabel joinLbl, usernameLbl, hostnameLbl;

	public JoinGameMenu() {
		super();
		joinLbl = new CatanJLabel("Select Join Port:");
		portTF = new CatanTextField(Integer.toString(LaunchMenu.lc.getJoinPort()));
		usernameLbl = new CatanJLabel("Select Username:");
		usernameTF = new CatanTextField(LaunchMenu.lc.getAvatarName());
		hostnameLbl = new CatanJLabel("Select Hostname:");
		hostnameTF = new CatanTextField(LaunchMenu.lc.getHostName());
		

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
					LaunchMenu.lc.setHostname(hn);
				}
				catch(NumberFormatException e1) {
					e1.printStackTrace();
				}
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
					LaunchMenu.lc.setJoinPort(joinPort);
				}
				catch(NumberFormatException e1) {
					e1.printStackTrace();
				}
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
				LaunchMenu.lc.setAvatarName(usernameTF.getText());
			}
		});

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						LaunchMenu.frame.setPage(new MainMenu());
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
						final JoinGameLoadingMenu jglm = new JoinGameLoadingMenu();
						LaunchMenu.frame.setPage(jglm);
						jglm.loadClient();
					}
				});
			}
		});

		addButton(hostnameLbl);
		addButton(hostnameTF);
		addButton(joinLbl);
		addButton(portTF);
		addButton(usernameLbl);
		addButton(usernameTF);
		addButton(submit);
		addButton(back);
	}
}
