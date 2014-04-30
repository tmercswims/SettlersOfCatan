/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.eheimark.catan.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;

public class AlphaContainer extends JComponent {
    
    private static final long serialVersionUID = 2029382013653951666L;
    
    private final JComponent _component;

    public AlphaContainer(JComponent component) {
        super();
        _component = component;
        setLayout(new BorderLayout());
        setOpaque(false);
        _component.setOpaque(false);
        add(_component, BorderLayout.CENTER);
        setBackground(_component.getBackground());
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        super.paintComponent(g);
    }
}
