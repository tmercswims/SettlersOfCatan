package edu.brown.cs032.eheimark.catan.gui.navigator;

import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.felt;

import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Color;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.Update;
import edu.brown.cs032.sbreslow.catan.gui.board.BoardImages;
import edu.brown.cs032.sbreslow.catan.gui.devCards.BackgroundPanel;
import edu.brown.cs032.tmercuri.catan.logic.Player;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

/**
 * The Class Overview is the overview tabbed panel which contains
 * basic game info like number of victory points, user names, number of cards
 * per player, etc.
 */
public class Overview extends JPanel implements Update {
	private static final long serialVersionUID = 1L;
	// TODO Fix background images
	private static final Color MY_BACKGROUND = Constants.CATAN_WHITE;
	private final CatanClient client;
	private final ArrayList<PlayerStats> playerstats;
	private final MyRenderer myColorRenderer;
	private final MyTableModel myTableModel;


	/**
	 * Instantiates a new overview panel.
	 *
	 * @param cc the cc
	 */
	public Overview(CatanClient cc) {
		super();
		this.setLayout(new GridLayout(1, 0));
		setBackground(MY_BACKGROUND);
		playerstats = new ArrayList<PlayerStats>();
		myTableModel = new MyTableModel();
		myColorRenderer = new MyRenderer();
		final JTable table = new JTable(myTableModel);
		table.setDefaultRenderer(Object.class, myColorRenderer);
		table.getTableHeader().setFont(Constants.OVERVIEW_TAB_FONT_HEADER);
		table.getTableHeader().setAlignmentX(SwingConstants.CENTER);
		table.setShowGrid(true);
		table.setOpaque(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(20);
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		add(scrollPane);
		this.client = cc;
	}

	public Color getTableCellBackground(JTable table, int row, int col) {
		TableCellRenderer renderer = table.getCellRenderer(row, col);
		Component component = table.prepareRenderer(renderer, row, col);    
		return component.getBackground();
	}

	class MyRenderer implements TableCellRenderer {
		private HashMap<Integer, Color> rowColors;
		private int activePlayerRow = 0;

		public MyRenderer() {
			rowColors = new HashMap<Integer, Color>();
		}

		public void addColor(int row, Color c) {
			rowColors.put(row, c);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			JTextField editor = new JTextField();
			if (value != null) {
				editor.setText(value.toString());
				editor.setHorizontalAlignment(SwingConstants.CENTER);
			}
			if(rowColors.size() > row) {
				editor.setBackground(rowColors.get(row));
				editor.setForeground(Constants.CATAN_YELLOW);
			}
			editor.setFont(Constants.OVERVIEW_TAB_FONT);
//			editor.setBorder(null);
			editor.setOpaque(true);
			return editor;
		}
	}

	class MyTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private String[] columnNames = {"Name",
				"Victory Points",
				"Resource Cards",
				"Development Cards",
				"Roads",
				"Cities",
		"Settlements"};
		private Object[][] data = {{"Name",
			"Victory Points",
			"Resource Cards",
			"Development Cards",
			"Roads",
			"Cities",
		"Settlements"}};

		public void initializeData(int numberPlayers) {
			data = new Object[numberPlayers][7];
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public void updatePlayer(Player p, int row) {
			int column = 0;
			data[row][column++] = p.getName();
			data[row][column++] = p.getVictoryPoints();
			data[row][column++] = p.getTotalResources();
			data[row][column++] = p.getDevCardCount();
			data[row][column++] = p.getRoadsBuilt();
			data[row][column++] = p.getCitiesBuilt();
			data[row][column++] = p.getSettlementsBuilt();
			myColorRenderer.addColor(row, p.getColor());
//			if(p.isActive()) {
//				myColorRenderer.setActivePlayerRow(row);
//			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image background = felt;
		int iw = background.getWidth(this);
		int ih = background.getHeight(this);
		if (iw > 0 && ih > 0) {
			for (int x = 0; x < getWidth(); x += iw) {
				for (int y = 0; y < getHeight(); y += ih) {
					g.drawImage(background, x, y, iw, ih, this);
				}
			}
		}
	}

	@Override
	public void ericUpdate() {
		Player[] players = this.client.getPlayers();
		myTableModel.initializeData(players.length);
		int row = 0;
		for(Player p : players) {
			myTableModel.updatePlayer(p, row++);
		}
		repaint();
	}
}
