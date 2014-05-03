package edu.brown.cs032.eheimark.catan.launch;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.newdawn.easyogg.OggClip;

/**
 * The Class CatanFrame is a generic JFrame for the Catan game.
 */
public class CatanFrame extends JFrame {
	private static final long serialVersionUID = 1L;
    
    private OggClip _music;

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
        setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    private void setMusic() {
        try {
            _music = new OggClip("music/menu.ogg");
            _music.setGain(.75f);
            _music.loop();
        } catch (IOException ex) {
            System.err.println(String.format("ERROR: %s", ex.getMessage()));
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
        _music.setGain(.75f);
        _music.loop();
    }

	/**
	 * Sets the page.
	 *
	 * @param page the new page to swap out old JPanel for
	 */
	public void setPage(JPanel page) {
		setContentPane(page);
		page.requestFocus();
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
	}
}
