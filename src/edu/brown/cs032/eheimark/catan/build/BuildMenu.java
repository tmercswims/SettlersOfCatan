package edu.brown.cs032.eheimark.catan.build;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

import java.awt.Font;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.brown.cs032.eheimark.catan.jcomponents.CatanJLabel;
import edu.brown.cs032.eheimark.catan.menu.Constants;

// Generic background panel for Catan Menu
public class BuildMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnCity;
	private JButton btnDevCard;
	private JButton btnRoad;
	private JLabel lblNewLabel;
	private JLabel lblvpCost;
	private JLabel lblvpCost_1;
	private JLabel lblCostOr;
	private JLabel lblBuildAResource;
	private JLabel lblCurrentResources;
	private JLabel lblSettlements;
	private JLabel lblCities;
	private JLabel lblRoads;
	private JLabel lblDevCards;
	
	
	/**
	 * Switch to net positive and net negative rather than two lines. Kick up the font size.
	 * 
	 */

	public BuildMenu() {
		super();
		setOpaque(true);
		setBackground(Color.WHITE);

		setPreferredSize(Constants.BUILD_MENU_SIZE);
		setMaximumSize(Constants.BUILD_MENU_SIZE);
		setMinimumSize(Constants.BUILD_MENU_SIZE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{116, 164, 133, 130, 0};
		gridBagLayout.rowHeights = new int[]{15, 20, 0, 24, 26, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblBuildAResource = new JLabel("Build:");
		lblBuildAResource.setBackground(Color.WHITE);
		lblBuildAResource.setForeground(Constants.CATAN_RED);
		lblBuildAResource.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		GridBagConstraints gbc_lblBuildAResource = new GridBagConstraints();
		gbc_lblBuildAResource.anchor = GridBagConstraints.SOUTH;
		gbc_lblBuildAResource.gridwidth = 2;
		gbc_lblBuildAResource.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuildAResource.gridx = 1;
		gbc_lblBuildAResource.gridy = 0;
		add(lblBuildAResource, gbc_lblBuildAResource);
		
		btnDevCard = new JButton("Dev Card");
		btnDevCard.setForeground(Constants.CATAN_RED);
		btnDevCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Build Dev Card");
			}
		});
		
		btnCity = new JButton("City");
		btnCity.setForeground(Constants.CATAN_RED);
		btnCity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Build City");
			}
		});
		
		btnRoad = new JButton("Road");
		btnRoad.setForeground(Constants.CATAN_RED);
		btnRoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Build Road");
			}
		});
		GridBagConstraints gbc_btnRoad = new GridBagConstraints();
		gbc_btnRoad.insets = new Insets(0, 0, 5, 5);
		gbc_btnRoad.gridx = 0;
		gbc_btnRoad.gridy = 1;
		add(btnRoad, gbc_btnRoad);
		
		JButton btnSettlement = new JButton("Settlement");
		btnSettlement.setForeground(Constants.CATAN_RED);
		btnSettlement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Build Settlement");
			}
		});
		GridBagConstraints gbc_btnSettlement = new GridBagConstraints();
		gbc_btnSettlement.insets = new Insets(0, 0, 5, 5);
		gbc_btnSettlement.gridx = 1;
		gbc_btnSettlement.gridy = 1;
		add(btnSettlement, gbc_btnSettlement);
		GridBagConstraints gbc_btnCity = new GridBagConstraints();
		gbc_btnCity.insets = new Insets(0, 0, 5, 5);
		gbc_btnCity.gridx = 2;
		gbc_btnCity.gridy = 1;
		add(btnCity, gbc_btnCity);
		GridBagConstraints gbc_btnDevCard = new GridBagConstraints();
		gbc_btnDevCard.insets = new Insets(0, 0, 5, 0);
		gbc_btnDevCard.gridx = 3;
		gbc_btnDevCard.gridy = 1;
		add(btnDevCard, gbc_btnDevCard);
		
		lblvpCost_1 = new JLabel("0VP, Cost: 1 Br 1 Lu");
		lblvpCost_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblvpCost_1 = new GridBagConstraints();
		gbc_lblvpCost_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblvpCost_1.gridx = 0;
		gbc_lblvpCost_1.gridy = 2;
		add(lblvpCost_1, gbc_lblvpCost_1);
		
		lblNewLabel = new JLabel("1VP, Cost: 1 Br 1 Lu 1 Wo 1 Gr");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		add(lblNewLabel, gbc_lblNewLabel);
		
		lblvpCost = new JLabel("2VP, Cost: 3 Or 2 Gr");
		lblvpCost.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblvpCost = new GridBagConstraints();
		gbc_lblvpCost.insets = new Insets(0, 0, 5, 5);
		gbc_lblvpCost.gridx = 2;
		gbc_lblvpCost.gridy = 2;
		add(lblvpCost, gbc_lblvpCost);
		
		lblCostOr = new JLabel("Cost: 1 Or Wo 1 Gr");
		lblCostOr.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblCostOr = new GridBagConstraints();
		gbc_lblCostOr.insets = new Insets(0, 0, 5, 0);
		gbc_lblCostOr.gridx = 3;
		gbc_lblCostOr.gridy = 2;
		add(lblCostOr, gbc_lblCostOr);
		
		lblCurrentResources = new JLabel("Current Possessions:");
		lblCurrentResources.setBackground(Color.WHITE);
		lblCurrentResources.setForeground(Constants.CATAN_BLUE);
		lblCurrentResources.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		GridBagConstraints gbc_lblCurrentResources = new GridBagConstraints();
		gbc_lblCurrentResources.anchor = GridBagConstraints.SOUTH;
		gbc_lblCurrentResources.gridwidth = 2;
		gbc_lblCurrentResources.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentResources.gridx = 1;
		gbc_lblCurrentResources.gridy = 3;
		add(lblCurrentResources, gbc_lblCurrentResources);
		
		lblSettlements = new JLabel("Settlements: #");
		lblSettlements.setBackground(Color.WHITE);
		lblSettlements.setForeground(Constants.CATAN_BLUE);
		lblSettlements.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		GridBagConstraints gbc_lblSettlements = new GridBagConstraints();
		gbc_lblSettlements.insets = new Insets(0, 0, 0, 5);
		gbc_lblSettlements.gridx = 0;
		gbc_lblSettlements.gridy = 4;
		add(lblSettlements, gbc_lblSettlements);
		
		lblCities = new JLabel("Cities: #");
		lblCities.setBackground(Color.WHITE);
		lblCities.setForeground(Constants.CATAN_BLUE);
		lblCities.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		GridBagConstraints gbc_lblCities = new GridBagConstraints();
		gbc_lblCities.insets = new Insets(0, 0, 0, 5);
		gbc_lblCities.gridx = 1;
		gbc_lblCities.gridy = 4;
		add(lblCities, gbc_lblCities);
		
		lblRoads = new JLabel("Roads: #");
		lblRoads.setBackground(Color.WHITE);
		lblRoads.setForeground(Constants.CATAN_BLUE);
		lblRoads.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		GridBagConstraints gbc_lblRoads = new GridBagConstraints();
		gbc_lblRoads.insets = new Insets(0, 0, 0, 5);
		gbc_lblRoads.gridx = 2;
		gbc_lblRoads.gridy = 4;
		add(lblRoads, gbc_lblRoads);
		
		lblDevCards = new JLabel("Dev Cards: #");
		lblDevCards.setBackground(Color.WHITE);
		lblDevCards.setForeground(Constants.CATAN_BLUE);
		lblDevCards.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		GridBagConstraints gbc_lblDevCards = new GridBagConstraints();
		gbc_lblDevCards.gridx = 3;
		gbc_lblDevCards.gridy = 4;
		add(lblDevCards, gbc_lblDevCards);
	}
}
