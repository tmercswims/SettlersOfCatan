package edu.brown.cs032.eheimark.catan.gui.panels;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Background.felt;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_WHITE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Fonts.OVERVIEW_TAB_FONT;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Fonts.OVERVIEW_TAB_FONT_HEADER;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc.arrow;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.ServerUpdate;
import edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Edge;
import edu.brown.cs032.tmercuri.catan.logic.Player;

/**
 * The Class Overview is the overview tabbed panel which contains
 * basic game info like number of victory points, user names, number of cards
 * per player, etc.
 */
public class Overview extends JPanel implements ServerUpdate {
	private static final long serialVersionUID = 3391138866753212845L;
	private static final Color MY_BACKGROUND = CATAN_WHITE;
	private final CatanClient client; // Reference to client
	private final MyRenderer myColorRenderer; // Custom renderer
	private final MyTableModel myTableModel; // Table Model for containing player information

	/**
	 * Instantiates a new overview panel.
	 *
	 * @param cc the cc
	 */
	public Overview(CatanClient cc) {
		super();
		this.setLayout(new GridLayout(1, 0));
		setBackground(MY_BACKGROUND);
		myTableModel = new MyTableModel();
		myColorRenderer = new MyRenderer();
		final JTable table = new JTable(myTableModel);
		table.setDefaultRenderer(Object.class, myColorRenderer);
		table.getTableHeader().setFont(OVERVIEW_TAB_FONT_HEADER);
		table.getTableHeader().setAlignmentX(SwingConstants.CENTER);
		table.setShowGrid(false);
		table.setOpaque(false);
		table.getColumnModel().getColumn(0).setMaxWidth(28);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(23);
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));		

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		add(scrollPane);
		this.client = cc;
	}

	/**
	 * Gets the table cell background.
	 *
	 * @param table the table
	 * @param row the row
	 * @param col the col
	 * @return the table cell background
	 */
	public Color getTableCellBackground(JTable table, int row, int col) {
		TableCellRenderer renderer = table.getCellRenderer(row, col);
		Component component = table.prepareRenderer(renderer, row, col);    
		return component.getBackground();
	}

	/**
	 * The Class MyRenderer is a custom renderer for the overview panel table.
	 */
	class MyRenderer implements TableCellRenderer {
		private final HashMap<Integer, Color> rowColors;
		private int activePlayerRow = 0;

		/**
		 * Instantiates a new my renderer.
		 */
		public MyRenderer() {
			rowColors = new HashMap<>();
		}

		/**
		 * Adds the color.
		 *
		 * @param row the row
		 * @param c the c
		 */
		public void addColor(int row, Color c) {
			rowColors.put(row, c);
		}

		/**
		 *@param the table
		 *@param the value
		 *@param is selected boolean
		 *@return custom rendered component
		 */
        @Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			if(column > 0) {
				JTextField editor = new JTextField();
				if (value != null) {
					editor.setText(value.toString());
					editor.setHorizontalAlignment(SwingConstants.CENTER);
				}
				if(rowColors.size() > row) {
					editor.setBackground(rowColors.get(row));
				}
				editor.setFont(OVERVIEW_TAB_FONT);
				editor.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				editor.setOpaque(true);
				return editor;
			}
			else {
				if(row == activePlayerRow) {
					return new JLabel(arrow);
				}
				else {
					return new JLabel();
				}
			}
		}


		/**
		 * Sets the active player row.
		 *
		 * @param row the new active player row
		 */
		public void setActivePlayerRow(int row) {
			activePlayerRow = row;
		}
	}

	/**
	 * Class that contains table information based on players.
	 */
	class MyTableModel extends AbstractTableModel {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The column names. */
		private final String[] columnNames = {"",
				"Name",
				"Victory Points",
				"Resource Cards",
				"Development Cards",
				"Roads",
				"Cities",
		"Settlements"};

		/** The data. */
		private Object[][] data = {{"",
			"Name",
			"Victory Points",
			"Resource Cards",
			"Development Cards",
			"Roads",
			"Cities",
		"Settlements"}};

		/**
		 * Initialize data.
		 *
		 * @param numberPlayers the number players
		 */
		public void initializeData(int numberPlayers) {
			data = new Object[numberPlayers][8];
		}

		/**
		 * Returns column count.
		 */
        @Override
		public int getColumnCount() {
			return columnNames.length;
		}

		/**
		 * Returns column count
		 */
        @Override
		public int getRowCount() {
			return data.length;
		}

		/**
		 * Returns column name
		 * @return the String
		 */
        @Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		/**
		 * Returns object
		 */
        @Override
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		/**
		 * Update player information in table.
		 *
		 * @param p the player
		 * @param row the row
		 */
		public void updatePlayer(Player p, int row) {
			int column = 1;
			data[row][column++] = p.getName();
			data[row][column++] = p.getVictoryPoints();
			data[row][column++] = p.getTotalResources();
			data[row][column++] = p.getDevCardCount();
			data[row][column++] = p.getRoadsBuilt();
			data[row][column++] = p.getCitiesBuilt();
			data[row][column++] = p.getSettlementsBuilt();
			if(p.getColor().equals(Edge.orange)){
				myColorRenderer.addColor(row, p.getColor());
			}
			else{
				myColorRenderer.addColor(row, p.getColor().brighter());
			}
			if(p.isActive()) {
				myColorRenderer.setActivePlayerRow(row);
			}
		}
	}

	/**
	 * Paints the overview.
	 *
	 * @param g the g
	 */
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

	/**
	 * Updates GUI with latest info from server.
	 */
	@Override
	public void serverUpdate() {
		Player[] players = this.client.getPlayers();
		myTableModel.initializeData(players.length);
		int row = 0;
		for(Player p : players) {
			myTableModel.updatePlayer(p, row++);
		}
		repaint();
	}
}
