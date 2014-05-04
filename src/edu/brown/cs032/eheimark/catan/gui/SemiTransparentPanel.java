/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.eheimark.catan.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Thomas Mercurio
 */
public class SemiTransparentPanel extends JPanel {
    
    private static final long serialVersionUID = 8687711032200940057L;
    
    public SemiTransparentPanel() {
        super();
        this.setLayout(new BorderLayout());
        setOpaque(false);
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	Color c = getBackground();
    	if(c.getAlpha()<205)
    		g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()+50));
        Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        super.paintComponent(g);
    }
}
