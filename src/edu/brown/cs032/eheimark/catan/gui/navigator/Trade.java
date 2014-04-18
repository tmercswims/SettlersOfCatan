package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import edu.brown.cs032.eheimark.catan.menu.Constants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Trade extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Image img; // background image
	private final String IMG_FILE_LOC = "images/Cards1000x140.png";

	private static final Font MY_FONT = new Font("Georgia", Font.BOLD, 14);
	private static final Color MY_BACKGROUND = Constants.CATAN_RED;
	private static final Color MY_FOREGROUND = Constants.CATAN_YELLOW;
	
	public Trade() {
		super();
		setForeground(MY_FOREGROUND);

		this.img = new ImageIcon(IMG_FILE_LOC).getImage();

		setPreferredSize(Constants.TAB_MENU_SIZE);
		setMaximumSize(Constants.TAB_MENU_SIZE);
		setMinimumSize(Constants.TAB_MENU_SIZE);
		setLayout(null);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(53, 15, 117, 16);
		add(comboBox_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(155, 59, 38, 16);
		add(comboBox);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(328, 59, 38, 16);
		add(comboBox_2);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(501, 59, 38, 16);
		add(comboBox_3);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setBounds(674, 59, 38, 16);
		add(comboBox_4);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setBounds(847, 59, 38, 16);
		add(comboBox_5);
		
		JLabel brickLabel = new JLabel("Brick:");
		brickLabel.setFont(MY_FONT);
		brickLabel.setOpaque(true);
		brickLabel.setBackground(Color.WHITE);
		brickLabel.setBounds(801, 59, 44, 16);
		brickLabel.setForeground(Color.RED);
		add(brickLabel);
		
		JLabel lumberLabel = new JLabel("Lumber:");
		lumberLabel.setFont(MY_FONT);
		lumberLabel.setOpaque(true);
		lumberLabel.setBackground(Color.WHITE);
		lumberLabel.setBounds(610, 59, 63, 16);
		lumberLabel.setForeground(Color.GREEN);
		add(lumberLabel);
		
		JLabel woolLabel = new JLabel("Wool:");
		woolLabel.setFont(MY_FONT);
		woolLabel.setOpaque(true);
		woolLabel.setBackground(Color.WHITE);
		woolLabel.setBounds(456, 59, 45, 16);
		woolLabel.setForeground(Color.LIGHT_GRAY);
		add(woolLabel);
		
		JLabel grainLabel = new JLabel("Wheat:");
		grainLabel.setFont(MY_FONT);
		grainLabel.setOpaque(true);
		grainLabel.setBackground(Color.WHITE);
		grainLabel.setBounds(270, 59, 55, 16);
		grainLabel.setForeground(Color.ORANGE);
		add(grainLabel);
		
		JLabel oreLabel = new JLabel("Ore:");
		oreLabel.setFont(MY_FONT);
		oreLabel.setOpaque(true);
		oreLabel.setBackground(Color.WHITE);
		oreLabel.setBounds(123, 59, 31, 16);
		oreLabel.setForeground(Color.DARK_GRAY);
		add(oreLabel);
		
		JButton proposeButton = new JButton("Propose Offer");
		proposeButton.setFont(MY_FONT);
		proposeButton.setBounds(374, 99, 125, 29);
		add(proposeButton);
		
		JButton cancelButton = new JButton("Cancel Offers");
		cancelButton.setFont(MY_FONT);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		cancelButton.setBounds(499, 99, 122, 29);
		add(cancelButton);
		
		JLabel toLabel = new JLabel("To:");
		toLabel.setFont(MY_FONT);
		toLabel.setOpaque(true);
		toLabel.setForeground(Color.BLACK);
		toLabel.setBackground(Color.WHITE);
		toLabel.setBounds(28, 15, 138, 16);
		add(toLabel);
		
		JLabel clarificationLabel = new JLabel("+ = Incoming, - = Outgoing");
		clarificationLabel.setOpaque(true);
		clarificationLabel.setForeground(Color.BLACK);
		clarificationLabel.setFont(new Font("Times", Font.ITALIC, 12));
		clarificationLabel.setBackground(Color.WHITE);
		clarificationLabel.setBounds(28, 105, 136, 16);
		add(clarificationLabel);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(MY_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, null);
	}
}
