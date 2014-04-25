package edu.brown.cs032.eheimark.catan.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;


/**
 * The Class Constants contains constants used throughout the GUI.
 */
public class Constants {
	public static final Font DEFAULT_LABEL_FONT  = new Font("Georgia", Font.PLAIN, 14);
	public static final Font DEFAULT_BUTTON_FONT  = new Font("Georgia", Font.BOLD, 20);
	public static final Font MY_FONT_SMALL = new Font("Georgia", Font.BOLD, 13);
	
	public static final Color CATAN_RED = new Color(225,25,10);
	public static final Color CATAN_BLUE = Color.BLUE;
	public static final Color CATAN_BLACK = Color.BLACK;
	public static final Color CATAN_WHITE = Color.WHITE;
	public static final Color CATAN_YELLOW = Color.YELLOW;

	public static final Dimension DEFAULT_BUTTON_SIZE  = new Dimension(200, 50);
	public static final Dimension DEFAULT_MENU_SIZE  = new Dimension(700, 700);
	public static final Dimension TEXTAREA_SIZE = new Dimension(400, 200);
	public static final Dimension POINTS_MENU_SIZE  = new Dimension(675, 125);
	public static final Dimension MENU_SIZE  = new Dimension(1000, 200);
	public static final Dimension TAB_PANEL_MENU_SIZE = new Dimension(1000, 135); // each tab in tab panel
	public static final Dimension TABBED_MENU_SIZE = new Dimension(1000, 180); // tabbed panel overall
	public static final Dimension GUI_SIZE = new Dimension(1000, 1000);
	
	public static final String DEFAULT_HOSTNAME = "localhost";
	public static final String DEFAULT_PORT = "1700";
	
	public static final Image CATAN_LAUNCH_MENU_BACKGROUND =  new ImageIcon("images/CatanScaled700x700.png").getImage();
	public static final Image BUILD_IMAGE = new ImageIcon("images/CatanLogo1000x140Mask50.png").getImage();
	public static final Image TRADE_TAB_IMAGE = new ImageIcon("images/Cards1000x140.png").getImage();
}
