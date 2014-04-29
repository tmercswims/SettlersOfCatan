/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import edu.brown.cs032.sbreslow.catan.gui.board.BoardImages;
import edu.brown.cs032.tmercuri.catan.logic.Player;

public class TableRenderDemo extends JPanel {
	private static final long serialVersionUID = 1L;
	private final MyRenderer myColorRenderer;

	public TableRenderDemo() {
        super(new GridLayout(1, 0));
        myColorRenderer = new MyRenderer();
        final JTable table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(600, 200));
        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Object.class, myColorRenderer);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    public Color getTableCellBackground(JTable table, int row, int col) {
        TableCellRenderer renderer = table.getCellRenderer(row, col);
        Component component = table.prepareRenderer(renderer, row, col);    
        return component.getBackground();
    }

    class MyRenderer implements TableCellRenderer {
    	ArrayList<Color> rowColors;
    	
    	public MyRenderer() {
    		rowColors = new ArrayList<Color>();
    	}
    	
    	public void addColor(Color c) {
    		rowColors.add(c);
    	}
    	
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            JTextField editor = new JTextField();
            if (value != null) {
                editor.setText(value.toString());
            }
            editor.setForeground(rowColors.get(row));
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
        private Object[][] data = {
//            {"Name", "Victory Points", "Resource Cards","Development Cards", "Roads", "Cities", "Settlements"},
//            {"Name", "Victory Points", "Resource Cards","Development Cards", "Roads", "Cities", "Settlements"}
        };

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
        
        int row = 0;
        public void insertPlayer(Player p) {
        	int column = 0;
        	data[row][column] = p.getName();
        	data[row][column++] = p.getVictoryPoints();
        	data[row][column++] = p.getTotalResources();
        	data[row][column++] = p.getDevCardCount();
        	data[row][column++] = p.getRoadsBuilt();
        	data[row][column++] = p.getCitiesBuilt();
        	data[row][column++] = p.getSettlementsBuilt();
			Color c = p.getColor();
			if(!c.equals(BoardImages.Edge.white))
				myColorRenderer.addColor(c);
			else
				myColorRenderer.addColor(Color.black);
        }
    }

}