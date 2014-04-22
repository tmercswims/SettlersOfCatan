package edu.brown.cs032.eheimark.catan.launch;

import javax.swing.SwingUtilities;

import edu.brown.cs032.eheimark.catan.launch.screens.MainMenu;

/**
 * The Class SettlersOfCatan launches the game by bringing up a Settlers Main Menu from which users can create a server
 * or join an existing server as a client.
 */
public class SettlersOfCatan {
	private CatanFrame frame; // Reference to CatanFrame
	private LaunchConfiguration lc; // Reference to launch configuration

	/**
	 * Instantiates a new settlers of catan game.
	 */
	public SettlersOfCatan() {
		setLaunchConfiguration(new LaunchConfiguration());
		setFrame(new CatanFrame(new MainMenu(this), "Settlers of Catan"));
	}

	/**
	 * Gets the frame.
	 *
	 * @return the frame
	 */
	public CatanFrame getFrame() {
		return frame;
	}

	/**
	 * Sets the frame.
	 *
	 * @param frame the new frame
	 */
	public void setFrame(CatanFrame frame) {
		this.frame = frame;
	}

	/**
	 * Gets the launch configuration.
	 *
	 * @return the launch configuration
	 */
	public LaunchConfiguration getLaunchConfiguration() {
		return lc;
	}

	/**
	 * Sets the launch configuration.
	 *
	 * @param lc the new launch configuration
	 */
	public void setLaunchConfiguration(LaunchConfiguration lc) {
		this.lc = lc;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SettlersOfCatan();
			}
		});
	}
}
