package edu.brown.cs032.eheimark.catan.launch;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc.DEFAULT_PORT;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc.DEFAULT_HOSTNAME;

/**
 * Class that maintains state of launch configuration. This information is then used by the Client/Server 
 * classes to launch the server and clients with the requested settings from the launch menus..
 */
public class LaunchConfiguration {	
	private boolean fourPlayerGame; // if true, 4 player game; if false, 3 player game
	private String joinPort; // port to connect to a server from "join" menu (acting only as client), 
	private String hostname; // host server name (IP address)
	private String hostPort; // port used when creating a server from "host" menu
	private String playerName; // player name once game launches

	/**
	 * Instantiates a new launch configuration.
	 */
	public LaunchConfiguration() {
		this.joinPort = DEFAULT_PORT;
		this.hostPort = DEFAULT_PORT;
		this.hostname = DEFAULT_HOSTNAME;
		setName("username");
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return playerName;
	}

	/**
	 * Sets the name.
	 *
	 * @param nameIn the new name
	 */
	public void setName(String nameIn) {
		playerName = nameIn;
	}

	/**
	 * Gets the join port.
	 *
	 * @return the join port
	 */
	public String getJoinPort() {
		return joinPort;
	}

	/**
	 * Sets the join port.
	 *
	 * @param joinPort the new join port
	 */
	public void setJoinPort(String joinPort) {
		this.joinPort = joinPort;
	}

	/**
	 * Gets the host port.
	 *
	 * @return the host port
	 */
	public String getHostPort() {
		return hostPort;
	}

	/**
	 * Sets the host port.
	 *
	 * @param hostPort the new host port
	 */
	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}

	/**
	 * Checks if is four player game.
	 *
	 * @return true, if is four player game
	 */
	public boolean isFourPlayerGame() {
		return fourPlayerGame;
	}

	/**
	 * Sets the four player game.
	 *
	 * @param fourPlayerGameIn the new four player game
	 */
	public void setFourPlayerGame(boolean fourPlayerGameIn) {
		fourPlayerGame = fourPlayerGameIn;
	}

	/**
	 * Gets the host name.
	 *
	 * @return the host name
	 */
	public String getHostName() {
		return hostname;
	}

	/**
	 * Sets the hostname.
	 *
	 * @param hn the new hostname
	 */
	public void setHostname(String hn) {
		hostname = hn;
	}
}
