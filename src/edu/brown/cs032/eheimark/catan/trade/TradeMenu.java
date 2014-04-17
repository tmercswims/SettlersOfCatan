package edu.brown.cs032.eheimark.catan.trade;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Insets;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

import edu.brown.cs032.eheimark.catan.menu.Constants;

import java.awt.Font;

// Generic background panel for Catan Menu
public class TradeMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Image img; // background image
	private final String IMG_FILE_LOC = "images/Cards700x330.png";

	public TradeMenu() {
		super();
		setForeground(Color.BLACK);

		this.img = new ImageIcon(IMG_FILE_LOC).getImage();

		setPreferredSize(Constants.TRADE_MENU_SIZE);
		setMaximumSize(Constants.TRADE_MENU_SIZE);
		setMinimumSize(Constants.TRADE_MENU_SIZE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{36, 44, 45, 45, 69, 58, 45, 45, 45, 45, 45, 45, 35, 0};
		gridBagLayout.rowHeights = new int[]{30, 27, 27, 51, 50, 0, 50, 47, 16, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblFrom = new JLabel("TRADE:");
		lblFrom.setOpaque(true);
		lblFrom.setBackground(Color.WHITE);
		lblFrom.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblFrom = new GridBagConstraints();
		gbc_lblFrom.insets = new Insets(0, 0, 5, 5);
		gbc_lblFrom.gridx = 1;
		gbc_lblFrom.gridy = 1;
		add(lblFrom, gbc_lblFrom);
		
		JTextArea textArea = new JTextArea();
		textArea.setText("<Name>");
		textArea.setEditable(false);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 4;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.gridx = 2;
		gbc_textArea.gridy = 1;
		add(textArea, gbc_textArea);
		
		JLabel lblTo = new JLabel("TRADE:");
		lblTo.setBackground(Color.WHITE);
		lblTo.setOpaque(true);
		lblTo.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTo.gridx = 1;
		gbc_lblTo.gridy = 2;
		add(lblTo, gbc_lblTo);
		
		JComboBox comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.gridwidth = 4;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 2;
		add(comboBox_1, gbc_comboBox_1);
		
		JLabel lblIn = new JLabel("SEND:");
		lblIn.setBackground(Color.WHITE);
		lblIn.setForeground(Color.BLACK);
		lblIn.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblIn.setOpaque(true);
		GridBagConstraints gbc_lblIn = new GridBagConstraints();
		gbc_lblIn.insets = new Insets(0, 0, 5, 5);
		gbc_lblIn.gridx = 1;
		gbc_lblIn.gridy = 4;
		add(lblIn, gbc_lblIn);
		
		JLabel lblBrick = new JLabel("Brick:");
		lblBrick.setOpaque(true);
		lblBrick.setBackground(Color.WHITE);
		lblBrick.setForeground(Color.RED);
		GridBagConstraints gbc_lblBrick = new GridBagConstraints();
		gbc_lblBrick.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
		gbc_lblBrick.insets = new Insets(0, 0, 5, 5);
		gbc_lblBrick.gridx = 2;
		gbc_lblBrick.gridy = 4;
		add(lblBrick, gbc_lblBrick);
		
		JComboBox comboBox_2 = new JComboBox();
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 3;
		gbc_comboBox_2.gridy = 4;
		add(comboBox_2, gbc_comboBox_2);
		
		JLabel label_2 = new JLabel("Lumber:");
		label_2.setOpaque(true);
		label_2.setForeground(Color.GREEN);
		label_2.setBackground(Color.WHITE);
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.gridx = 4;
		gbc_label_2.gridy = 4;
		add(label_2, gbc_label_2);
		
		JComboBox comboBox_5 = new JComboBox();
		GridBagConstraints gbc_comboBox_5 = new GridBagConstraints();
		gbc_comboBox_5.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_5.gridx = 5;
		gbc_comboBox_5.gridy = 4;
		add(comboBox_5, gbc_comboBox_5);
		
		JLabel lblOre = new JLabel("Wool:");
		lblOre.setForeground(Color.LIGHT_GRAY);
		lblOre.setBackground(Color.WHITE);
		lblOre.setOpaque(true);
		GridBagConstraints gbc_lblOre = new GridBagConstraints();
		gbc_lblOre.anchor = GridBagConstraints.EAST;
		gbc_lblOre.insets = new Insets(0, 0, 5, 5);
		gbc_lblOre.gridx = 6;
		gbc_lblOre.gridy = 4;
		add(lblOre, gbc_lblOre);
		
		JComboBox comboBox_6 = new JComboBox();
		GridBagConstraints gbc_comboBox_6 = new GridBagConstraints();
		gbc_comboBox_6.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_6.gridx = 7;
		gbc_comboBox_6.gridy = 4;
		add(comboBox_6, gbc_comboBox_6);
		
		JLabel label_4 = new JLabel("Grain:");
		label_4.setOpaque(true);
		label_4.setForeground(Color.ORANGE);
		label_4.setBackground(Color.WHITE);
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.anchor = GridBagConstraints.EAST;
		gbc_label_4.gridx = 8;
		gbc_label_4.gridy = 4;
		add(label_4, gbc_label_4);
		
		JComboBox comboBox_8 = new JComboBox();
		GridBagConstraints gbc_comboBox_8 = new GridBagConstraints();
		gbc_comboBox_8.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_8.gridx = 9;
		gbc_comboBox_8.gridy = 4;
		add(comboBox_8, gbc_comboBox_8);
		
		JLabel lblLumber_1 = new JLabel("Ore:");
		lblLumber_1.setForeground(Color.DARK_GRAY);
		lblLumber_1.setBackground(Color.WHITE);
		lblLumber_1.setOpaque(true);
		GridBagConstraints gbc_lblLumber_1 = new GridBagConstraints();
		gbc_lblLumber_1.anchor = GridBagConstraints.EAST;
		gbc_lblLumber_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblLumber_1.gridx = 10;
		gbc_lblLumber_1.gridy = 4;
		add(lblLumber_1, gbc_lblLumber_1);
		
		JComboBox comboBox_11 = new JComboBox();
		GridBagConstraints gbc_comboBox_11 = new GridBagConstraints();
		gbc_comboBox_11.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_11.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_11.gridx = 11;
		gbc_comboBox_11.gridy = 4;
		add(comboBox_11, gbc_comboBox_11);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 3;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 8;
		add(btnNewButton, gbc_btnNewButton);
		
		JButton btnCancelClose = new JButton("Cancel / Close");
		btnCancelClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Trade.frame.exit();
			}
		});
		GridBagConstraints gbc_btnCancelClose = new GridBagConstraints();
		gbc_btnCancelClose.gridwidth = 3;
		gbc_btnCancelClose.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelClose.gridx = 7;
		gbc_btnCancelClose.gridy = 8;
		add(btnCancelClose, gbc_btnCancelClose);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}
