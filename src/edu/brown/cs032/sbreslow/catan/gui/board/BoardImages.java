/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.sbreslow.catan.gui.board;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Thomas Mercurio
 */
public class BoardImages {
    static class Node {
        public static final Image settlementRed = new ImageIcon("images/pieces/settlement_red.png").getImage();
        public static final Image settlementBlue = new ImageIcon("images/pieces/settlement_blue.png").getImage();
        public static final Image settlementOrange = new ImageIcon("images/pieces/settlement_orange.png").getImage();
        public static final Image settlementWhite = new ImageIcon("images/pieces/settlement_white.png").getImage();
        
        public static final Image cityRed = new ImageIcon("images/pieces/city_red.png").getImage();
        public static final Image cityBlue = new ImageIcon("images/pieces/city_blue.png").getImage();
        public static final Image cityOrange = new ImageIcon("images/pieces/city_orange.png").getImage();
        public static final Image cityWhite = new ImageIcon("images/pieces/city_white.png").getImage();
    }
    
    static class Edge {
        public static final Image northPort = new ImageIcon("images/ports/north.png").getImage();
        public static final Image northeastPort = new ImageIcon("images/ports/northeast.png").getImage();
        public static final Image northwestPort = new ImageIcon("images/ports/northwest.png").getImage();
        public static final Image southPort = new ImageIcon("images/ports/south.png").getImage();
        public static final Image southwestPort = new ImageIcon("images/ports/southwest.png").getImage();
        public static final Image southeastPort = new ImageIcon("images/ports/southeast.png").getImage();
        
        public static final Image brickPort = new ImageIcon("images/ports/brick.png").getImage();
        public static final Image genericPort = new ImageIcon("images/ports/generic.png").getImage();
        public static final Image orePort = new ImageIcon("images/ports/ore.png").getImage();
        public static final Image sheepPort = new ImageIcon("images/ports/sheep.png").getImage();
        public static final Image wheatPort = new ImageIcon("images/ports/wheat.png").getImage();
        public static final Image woodPort = new ImageIcon("images/ports/wood.png").getImage();
        
        public static final Color red = new Color(252, 1, 1);
        public static final Color blue = new Color(0, 88, 147);
        public static final Color orange = new Color(183, 122, 1);
        public static final Color white = new Color(252, 254, 254);
    }
    
    static class Tile {
        public static final Image brickTile = new ImageIcon("images/tiles/brick.png").getImage();
        public static final Image desertTile = new ImageIcon("images/tiles/desert.png").getImage();
        public static final Image oceanTile = new ImageIcon("images/tiles/ocean.png").getImage();
        public static final Image oreTile = new ImageIcon("images/tiles/ore.png").getImage();
        public static final Image sheepTile = new ImageIcon("images/tiles/sheep.png").getImage();
        public static final Image wheatTile = new ImageIcon("images/tiles/wheat.png").getImage();
        public static final Image woodTile = new ImageIcon("images/tiles/wood.png").getImage();

        public static final Image two = new ImageIcon("images/numbers/2.png").getImage();
        public static final Image three = new ImageIcon("images/numbers/3.png").getImage();
        public static final Image four = new ImageIcon("images/numbers/4.png").getImage();
        public static final Image five = new ImageIcon("images/numbers/5.png").getImage();
        public static final Image six = new ImageIcon("images/numbers/6.png").getImage();
        public static final Image eight = new ImageIcon("images/numbers/8.png").getImage();
        public static final Image nine = new ImageIcon("images/numbers/9.png").getImage();
        public static final Image ten = new ImageIcon("images/numbers/10.png").getImage();
        public static final Image eleven = new ImageIcon("images/numbers/11.png").getImage();
        public static final Image twelve = new ImageIcon("images/numbers/12.png").getImage();
        
        public static final Image robber = new ImageIcon("images/pieces/robber.png").getImage();
    }
    
    static class Misc {
        public static final Image arrow = new ImageIcon("images/misc/arrow.png").getImage();
    }
}
