package edu.brown.cs032.eheimark.catan.points;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JTextArea;
import edu.brown.cs032.eheimark.catan.menu.Constants;
import java.awt.Font;

// Generic background panel for Catan Menu
public class Score extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Image img; // background image
	private final String IMG_FILE_LOC = "images/Points700x150.png";

	private static final Font MY_FONT = new Font("Arial", Font.BOLD, 13);
	private static final Color MY_BACKGROUND = Constants.CATAN_RED;
	private static final Color MY_FOREGROUND = Constants.CATAN_YELLOW;
	
	public Score() {
		super();
		setForeground(MY_FOREGROUND);
		setBackground(MY_BACKGROUND);

		this.img = new ImageIcon(IMG_FILE_LOC).getImage();

		setPreferredSize(Constants.POINTS_MENU_SIZE);
		setMaximumSize(Constants.POINTS_MENU_SIZE);
		setMinimumSize(Constants.POINTS_MENU_SIZE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{12, 161, 42, 126, 93, 114, 55, 70, 0};
		gridBagLayout.rowHeights = new int[]{53, 110, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);


		JTextArea nameLabel = new JTextArea();
		nameLabel.setEditable(false);
		nameLabel.setForeground(MY_FOREGROUND);
		nameLabel.setBackground(MY_BACKGROUND);
		nameLabel.setText("name1141318\nname2\nname3\nname4");
		nameLabel.setFont(MY_FONT);

		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.fill = GridBagConstraints.BOTH;
		gbc_nameLabel.insets = new Insets(0, 0, 0, 5);
		gbc_nameLabel.gridx = 1;
		gbc_nameLabel.gridy = 1;
		add(nameLabel, gbc_nameLabel);
		
		JTextArea VPsTA = new JTextArea();
		VPsTA.setEditable(false);
		VPsTA.setBackground(MY_BACKGROUND);
		VPsTA.setForeground(MY_FOREGROUND);
		VPsTA.setText("1\n2\n3\n4");
		VPsTA.setFont(MY_FONT);

		GridBagConstraints gbc_VPsTA = new GridBagConstraints();
		gbc_VPsTA.insets = new Insets(0, 0, 0, 5);
		gbc_VPsTA.fill = GridBagConstraints.VERTICAL;
		gbc_VPsTA.gridx = 2;
		gbc_VPsTA.gridy = 1;
		add(VPsTA, gbc_VPsTA);
		
		JTextArea devCardsTA = new JTextArea();
		devCardsTA.setEditable(false);
		devCardsTA.setText("1\n2\n3\n4");
		devCardsTA.setForeground(MY_FOREGROUND);
		devCardsTA.setFont(MY_FONT);
		devCardsTA.setBackground(MY_BACKGROUND);
		
		GridBagConstraints gbc_devCardsTA = new GridBagConstraints();
		gbc_devCardsTA.insets = new Insets(0, 0, 0, 5);
		gbc_devCardsTA.fill = GridBagConstraints.VERTICAL;
		gbc_devCardsTA.gridx = 3;
		gbc_devCardsTA.gridy = 1;
		add(devCardsTA, gbc_devCardsTA);
		
		JTextArea resourcesTA = new JTextArea();
		resourcesTA.setEditable(false);
		resourcesTA.setText("12\n2\n3\n4");
		resourcesTA.setForeground(MY_FOREGROUND);
		resourcesTA.setFont(MY_FONT);
		resourcesTA.setBackground(MY_BACKGROUND);
		GridBagConstraints gbc_resourcesTA = new GridBagConstraints();
		gbc_resourcesTA.insets = new Insets(0, 0, 0, 5);
		gbc_resourcesTA.fill = GridBagConstraints.VERTICAL;
		gbc_resourcesTA.gridx = 4;
		gbc_resourcesTA.gridy = 1;
		add(resourcesTA, gbc_resourcesTA);
		
		JTextArea roadsTA = new JTextArea();
		roadsTA.setEditable(false);
		roadsTA.setText("1\n2\n33\n4");
		roadsTA.setForeground(MY_FOREGROUND);
		roadsTA.setFont(MY_FONT);
		roadsTA.setBackground(MY_BACKGROUND);
		GridBagConstraints gbc_roadsTA = new GridBagConstraints();
		gbc_roadsTA.insets = new Insets(0, 0, 0, 5);
		gbc_roadsTA.fill = GridBagConstraints.VERTICAL;
		gbc_roadsTA.gridx = 5;
		gbc_roadsTA.gridy = 1;
		add(roadsTA, gbc_roadsTA);
		
		JTextArea citiesTA = new JTextArea();
		citiesTA.setEditable(false);
		citiesTA.setText("1\n2\n3\n4");
		citiesTA.setForeground(MY_FOREGROUND);
		citiesTA.setFont(MY_FONT);
		citiesTA.setBackground(MY_BACKGROUND);
		GridBagConstraints gbc_citiesTA = new GridBagConstraints();
		gbc_citiesTA.insets = new Insets(0, 0, 0, 5);
		gbc_citiesTA.fill = GridBagConstraints.VERTICAL;
		gbc_citiesTA.gridx = 6;
		gbc_citiesTA.gridy = 1;
		add(citiesTA, gbc_citiesTA);
		
		JTextArea settlementsTA = new JTextArea();
		settlementsTA.setEditable(false);
		settlementsTA.setText("1\n2\n3\n4");
		settlementsTA.setForeground(MY_FOREGROUND);
		settlementsTA.setFont(MY_FONT);
		settlementsTA.setBackground(MY_BACKGROUND);
		GridBagConstraints gbc_settlementsTA = new GridBagConstraints();
		gbc_settlementsTA.fill = GridBagConstraints.VERTICAL;
		gbc_settlementsTA.gridx = 7;
		gbc_settlementsTA.gridy = 1;
		add(settlementsTA, gbc_settlementsTA);

	}

	public void paintComponent(Graphics g) {
		g.setColor(MY_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, null);
	}
}
