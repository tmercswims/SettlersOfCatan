package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JTextArea;

import edu.brown.cs032.eheimark.catan.menu.Constants;

import java.awt.Font;

public class Overview extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Image img; // background image
	private final String IMG_FILE_LOC = "images/overview.png";
	private static final Font MY_FONT = new Font("Georgia", Font.BOLD, 14);
	private static final Color MY_BACKGROUND = Constants.CATAN_RED;
	private static final Color MY_FOREGROUND = Constants.CATAN_YELLOW;
	
	public Overview() {
		super();
		setForeground(MY_FOREGROUND);
		setBackground(MY_BACKGROUND);

		this.img = new ImageIcon(IMG_FILE_LOC).getImage();

		setPreferredSize(Constants.TAB_MENU_SIZE);
		setMaximumSize(Constants.TAB_MENU_SIZE);
		setMinimumSize(Constants.TAB_MENU_SIZE);
		setLayout(null);


		JTextArea nameLabel = new JTextArea();
		nameLabel.setBounds(6, 60, 165, 70);
		nameLabel.setEditable(false);
		nameLabel.setForeground(MY_FOREGROUND);
		nameLabel.setBackground(MY_BACKGROUND);
		nameLabel.setText("name1141318\nname2\nname3\nname4");
		nameLabel.setFont(MY_FONT);
		add(nameLabel);
		
		JTextArea VPsTA = new JTextArea();
		VPsTA.setBounds(205, 60, 9, 70);
		VPsTA.setEditable(false);
		VPsTA.setBackground(MY_BACKGROUND);
		VPsTA.setForeground(MY_FOREGROUND);
		VPsTA.setText("1\n2\n3\n4");
		VPsTA.setFont(MY_FONT);
		add(VPsTA);
		
		JTextArea devCardsTA = new JTextArea();
		devCardsTA.setBounds(355, 60, 9, 70);
		devCardsTA.setEditable(false);
		devCardsTA.setText("1\n2\n3\n4");
		devCardsTA.setForeground(MY_FOREGROUND);
		devCardsTA.setFont(MY_FONT);
		devCardsTA.setBackground(MY_BACKGROUND);
		add(devCardsTA);
		
		JTextArea resourcesTA = new JTextArea();
		resourcesTA.setBounds(512, 60, 16, 70);
		resourcesTA.setEditable(false);
		resourcesTA.setText("12\n2\n3\n4");
		resourcesTA.setForeground(MY_FOREGROUND);
		resourcesTA.setFont(MY_FONT);
		resourcesTA.setBackground(MY_BACKGROUND);
		add(resourcesTA);
		
		JTextArea roadsTA = new JTextArea();
		roadsTA.setBounds(662, 60, 18, 70);
		roadsTA.setEditable(false);
		roadsTA.setText("1\n2\n33\n4");
		roadsTA.setForeground(MY_FOREGROUND);
		roadsTA.setFont(MY_FONT);
		roadsTA.setBackground(MY_BACKGROUND);
		add(roadsTA);
		
		JTextArea citiesTA = new JTextArea();
		citiesTA.setBounds(816, 60, 9, 70);
		citiesTA.setEditable(false);
		citiesTA.setText("1\n2\n3\n4");
		citiesTA.setForeground(MY_FOREGROUND);
		citiesTA.setFont(MY_FONT);
		citiesTA.setBackground(MY_BACKGROUND);
		add(citiesTA);
		
		JTextArea settlementsTA = new JTextArea();
		settlementsTA.setBounds(945, 60, 9, 70);
		settlementsTA.setEditable(false);
		settlementsTA.setText("1\n2\n3\n4");
		settlementsTA.setForeground(MY_FOREGROUND);
		settlementsTA.setFont(MY_FONT);
		settlementsTA.setBackground(MY_BACKGROUND);
		add(settlementsTA);

	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(MY_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, null);
	}
}
