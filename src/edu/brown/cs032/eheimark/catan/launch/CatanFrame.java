package edu.brown.cs032.eheimark.catan.launch;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Class CatanFrame is a generic JFrame for the Catan game.
 */
public class CatanFrame extends JFrame {
	private static final long serialVersionUID = 1L;
    
    private Clip _music;
    private AudioInputStream _stream;

	/**
	 * Instantiates a new Catan frame.
	 *
	 * @param p the initial pane
	 * @param name the name of the frame
	 */
	public CatanFrame(JPanel p, String name) {
		super(name);
		setPage(p);
        setMusic();
		setVisible(true);
		setResizable(false);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    private void setMusic() {
        _stream = null;
        try {
            _stream = AudioSystem.getAudioInputStream(new File("music/menu.wav"));
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

	/**
	 * Sets the page.
	 *
	 * @param page the new page to swap out old JPanel for
	 */
	public void setPage(JPanel page) {
		setContentPane(page);
		pack();
		repaint();
	}

	/**
	 * Exits JFrame.
	 */
	public void exit() {
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
