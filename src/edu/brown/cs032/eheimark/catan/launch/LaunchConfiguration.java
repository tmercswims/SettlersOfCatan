package edu.brown.cs032.eheimark.catan.launch;

// TODO: Auto-generated Javadoc
/**
 * Class that maintains state of launch configuration. This information is then used by the Client/Server classes to launch 
 * the server and clients with the requested settings from the launch menus..
 *
 */
public class LaunchConfiguration {	
	private boolean FourPlayerGame; // if true, 4 player game; if false, 3 player game
	private int joinPort; // port to connect to a server from "join" menu (acting only as client), 
	private String hostname; // host server name (IP address)
	private int hostPort; // port used when creating a server from "host" menu
	private String name; // player name once game launches
	private int boardSize; // small = 0, medium = 1, large = 2
	private boolean inGameHelpOn; // whether in-game help is on
	public static final int SMALL_BOARD = 0, MEDIUM_BOARD = 1, LARGE_BOARD = 2;
	public static final int DEFAULT_PORT = 1700;
	public static final String DEFAULT_HOSTNAME = "192.168.0.11";

	/**
	 * Instantiates a new launch configuration.
	 */
	public LaunchConfiguration() {
		this.joinPort = DEFAULT_PORT;
		this.hostPort = DEFAULT_PORT;
		this.hostname = DEFAULT_HOSTNAME;
		setBoardSize(MEDIUM_BOARD);
		setInGameHelpOn(true);
		setName("username");
	}

	/**
	 * Checks if is in game help on.
	 *
	 * @return true, if is in game help on
	 */
	public boolean isInGameHelpOn() {
		return inGameHelpOn;
	}

	/**
	 * Sets the in game help on.
	 *
	 * @param inGameHelpOn the new in game help on
	 */
	public void setInGameHelpOn(boolean inGameHelpOn) {
		this.inGameHelpOn = inGameHelpOn;
	}

	/**
	 * Gets the board size.
	 *
	 * @return the board size
	 */
	public int getBoardSize() {
		return boardSize;
	}

	/**
	 * Sets the board size.
	 *
	 * @param boardSize the new board size
	 */
	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param nameIn the new name
	 */
	public void setName(String nameIn) {
		this.name = nameIn;
	}

	/**
	 * Gets the join port.
	 *
	 * @return the join port
	 */
	public int getJoinPort() {
		return joinPort;
	}

	/**
	 * Sets the join port.
	 *
	 * @param joinPort the new join port
	 */
	public void setJoinPort(int joinPort) {
		this.joinPort = joinPort;
	}

	/**
	 * Gets the host port.
	 *
	 * @return the host port
	 */
	public int getHostPort() {
		return hostPort;
	}

	/**
	 * Sets the host port.
	 *
	 * @param hostPort the new host port
	 */
	public void setHostPort(int hostPort) {
		this.hostPort = hostPort;
	}

	/**
	 * Checks if is four player game.
	 *
	 * @return true, if is four player game
	 */
	public boolean isFourPlayerGame() {
		return FourPlayerGame;
	}

	/**
	 * Sets the four player game.
	 *
	 * @param fourPlayerGame the new four player game
	 */
	public void setFourPlayerGame(boolean fourPlayerGame) {
		FourPlayerGame = fourPlayerGame;
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
