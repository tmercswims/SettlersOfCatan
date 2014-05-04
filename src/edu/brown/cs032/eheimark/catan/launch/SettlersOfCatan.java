package edu.brown.cs032.eheimark.catan.launch;

import javax.swing.SwingUtilities;

import edu.brown.cs032.eheimark.catan.launch.screens.MainMenu;

/**
 * The Class SettlersOfCatan launches the game by bringing up a Settlers Menu from which users can create a server
 * or join an existing server as a client. Once three or four players have joined up, the game is launched.
 */
public class SettlersOfCatan {
	private LaunchFrame frame; // Reference to CatanFrame
	private final LaunchConfiguration launchConfig; // Reference to launch configuration

	/**
	 * Instantiates a new settlers of catan launch menu.
	 */
	public SettlersOfCatan() {
		launchConfig = new LaunchConfiguration();
		setFrame(new LaunchFrame(new MainMenu(this), "Settlers of Catan"));
	}

	/**
	 * Gets the frame.
	 *
	 * @return the frame
	 */
	public LaunchFrame getFrame() {
		return frame;
	}

	/**
	 * Sets the frame.
	 *
	 * @param frame the new frame
	 */
	public void setFrame(LaunchFrame frame) {
		this.frame = frame;
		frame.pack();
	}

	/**
	 * Gets the launch configuration.
	 *
	 * @return the launch configuration
	 */
	public LaunchConfiguration getLaunchConfiguration() {
		return launchConfig;
	}

	/**
	 * Launches the Settlers of Catan game by calling the launch menu. The launch
	 * menu is then used to set game settings and launch the board etc.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SettlersOfCatan();
			}
		});
	}
}
