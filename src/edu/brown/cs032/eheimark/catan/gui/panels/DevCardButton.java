package edu.brown.cs032.eheimark.catan.gui.panels;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * The Class DevCardButton is a JButton for devcards
 */
public class DevCardButton extends JButton {
	private static final long serialVersionUID = 1538809062862512149L;

	/**
     * Instantiates a new dev card button.
     *
     * @param icon the icon
     */
    public DevCardButton(Icon icon) {
        super(icon);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
    }
}
