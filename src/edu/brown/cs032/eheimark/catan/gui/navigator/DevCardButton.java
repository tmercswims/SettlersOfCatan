/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.eheimark.catan.gui.navigator;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author Thomas Mercurio
 */
public class DevCardButton extends JButton {
    
    public DevCardButton(Icon icon) {
        super(icon);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
    }
}
