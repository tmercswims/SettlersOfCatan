package edu.brown.cs032.eheimark.catan.gui;

import javax.swing.JFrame;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;

import org.newdawn.easyogg.OggClip;

/**
 * The Class GUIFrame contains the main gui panel with the board at top
 * and tabbed panels at the bottom.
 */
public class GUIFrame extends JFrame {
	private static final long serialVersionUID = 1L;
    
    private OggClip _music;

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
		setResizable(true);
		//setMinimumSize(Constants.GUI_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
        setLocationRelativeTo(null);
        addComponentListener(new SizeList(cc, this));
	}
	
	private class SizeList implements ComponentListener{

		private CatanClient _cc;
		private JFrame _frame;
		
		public SizeList(CatanClient cc, JFrame frame) {
			_cc = cc;
			_frame = frame;
		}

		@Override
		public void componentResized(ComponentEvent e) {
			_cc.getGUI().getDP().setSize(_frame.getWidth()*600/1000, _frame.getHeight()*600/850);
			_cc.getBoard().resize(_cc.getGUI().getDP().getWidth(), _cc.getGUI().getDP().getHeight());
			_cc.getGUI().getDP().ericUpdate();
			System.out.println(_cc.getGUI().getDP().getWidth());
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentShown(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}

		
		
	}
    
    private void setMusic() {
        try {
            _music = new OggClip("music/ingame.ogg");
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
        _music.loop();
    }
	
	public void exit(){
		super.setVisible(false);
		super.dispose();
        _music.stop();
	}

	public void toggleMusic() {
		if (_music.stopped()) {
			_music.loop();
		} else {
			_music.stop();
		}
	}

	/**
	 * Tells whether the music is playing.
	 * @return true if music is playing, false if not
	 */
	public boolean isMusicPlaying() {
		return !_music.stopped();
	}
}
