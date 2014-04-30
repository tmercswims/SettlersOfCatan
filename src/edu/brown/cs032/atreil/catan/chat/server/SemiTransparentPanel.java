/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.atreil.catan.chat.server;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 *
 * @author Thomas Mercurio
 */
public class SemiTransparentPanel extends JPanel {
    
    private static final long serialVersionUID = 8687711032200940057L;
    
    public SemiTransparentPanel() {
        super(new BorderLayout());
        setOpaque(false);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        super.paintComponent(g);
    }
}
