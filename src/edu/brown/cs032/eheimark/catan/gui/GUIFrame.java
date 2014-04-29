package edu.brown.cs032.eheimark.catan.gui;

import javax.swing.JFrame;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.launch.CatanFrame;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The Class GUIFrame contains the main gui panel with the board at top
 * and tabbed panels at the bottom.
 */
public class GUIFrame extends JFrame {
	private static final long serialVersionUID = 1L;
    
    private Clip _music;
    private AudioInputStream _stream;

	/**
	 * Instantiates a new GUI frame.
	 *
	 * @param cc the cc
	 */
	public GUIFrame(CatanClient cc) {
		super("Settlers of Catan : " + cc.getPlayerName());
        setMusic();
		add(new GUI(cc));
		setVisible(true);
		setResizable(false);
		setMinimumSize(Constants.GUI_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}
    
    private void setMusic() {
        _stream = null;
        try {
            _stream = AudioSystem.getAudioInputStream(new File("music/in_game.wav"));
            _music = AudioSystem.getClip();
            _music.open(_stream);
            _music.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.err.println(String.format("ERROR: %s", ex.getMessage()));
        } finally {
            try {
                if (_stream != null) _stream.close();
            } catch (IOException ex) {
                Logger.getLogger(CatanFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Stops the music.
     */
    public void stopMusic() {
        _music.stop();
    }
    
    /**
     * Plays the music.
     */
    public void playMusic() {
        _music.loop(Clip.LOOP_CONTINUOUSLY);
    }
	
	public void exit(){
		super.setVisible(false);
		super.dispose();
        _music.stop();
        try {
            _stream.close();
        } catch (IOException ex) {
            System.err.println(String.format("ERROR: %s", ex.getMessage()));
        }
	}
}
