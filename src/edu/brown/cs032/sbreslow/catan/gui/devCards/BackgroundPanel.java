package edu.brown.cs032.sbreslow.catan.gui.devCards;

import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.wood;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
	
	public BackgroundPanel(){
		super();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        Image background = wood;
        int iw = background.getWidth(this);
        int ih = background.getHeight(this);
        if (iw > 0 && ih > 0) {
            for (int x = 0; x < getWidth(); x += iw) {
                for (int y = 0; y < getHeight(); y += ih) {
                    System.out.println("DREW A BG TILE");
                    g.drawImage(background, x, y, iw, ih, this);
                }
            }
        }
	}
}
