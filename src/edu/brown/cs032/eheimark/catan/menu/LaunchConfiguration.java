package edu.brown.cs032.eheimark.catan.menu;

import java.awt.Image;

/**
 * Class that maintains state of launch configuration. This information is then used by the Client/Server classes to launch the server and clients.
 *
 */
public class LaunchConfiguration {	
	private boolean FourPlayerGame; // if false, 3 player game
	private int joinPort; // outsidehost used when connecting to a server from "join" menu (acting only as client), 
	private int hostPort; // localhost used when creating a server from "host" menu (acting as client + server)
	private String avatarName;
	private Image avatarImage; // Probably ignore for now
	private int boardSize; // small = 0, medium = 1, large = 2
	public static final int SMALL_BOARD = 0, MEDIUM_BOARD = 1, LARGE_BOARD = 2;
	public static final int DEFAULT_PORT = 1700;
	
	public LaunchConfiguration() {
		this.joinPort = DEFAULT_PORT;
		this.hostPort = DEFAULT_PORT;
		setBoardSize(MEDIUM_BOARD);
		setInGameHelpOn(true);
		setAvatarName("Default UserName");
	}
	
	private boolean inGameHelpOn;

	public boolean isInGameHelpOn() {
		return inGameHelpOn;
	}
	public void setInGameHelpOn(boolean inGameHelpOn) {
		this.inGameHelpOn = inGameHelpOn;
	}
	public int getBoardSize() {
		return boardSize;
	}
	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}
	public Image getAvatarImage() {
		return avatarImage;
	}
	public void setAvatarImage(Image avatarImage) {
		this.avatarImage = avatarImage;
	}
	public String getAvatarName() {
		return avatarName;
	}
	public void setAvatarName(String avatarName) {
		this.avatarName = avatarName;
	}
	public int getJoinPort() {
		return joinPort;
	}
	public void setJoinPort(int joinPort) {
		this.joinPort = joinPort;
	}
	public int getHostPort() {
		return hostPort;
	}
	public void setHostPort(int hostPort) {
		this.hostPort = hostPort;
	}
	public boolean isFourPlayerGame() {
		return FourPlayerGame;
	}
	public void setFourPlayerGame(boolean fourPlayerGame) {
		FourPlayerGame = fourPlayerGame;
	}
}
