/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.atreil.catan.networking.client;

import edu.brown.cs032.eheimark.catan.launch.LaunchConfiguration;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import static edu.brown.cs032.tmercuri.catan.logic.BuildConstants.CITY;
import static edu.brown.cs032.tmercuri.catan.logic.BuildConstants.ROAD;
import static edu.brown.cs032.tmercuri.catan.logic.BuildConstants.SETTLEMENT;
import edu.brown.cs032.tmercuri.catan.logic.move.BuildMove;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * 
 * @author Thomas Mercurio
 */
public class Tester {
    
    private static Scanner scanner;

	/**
     * @param args the command line arguments
     */
	public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: <hostname> <port>");
        }
        try {
            Player p = new Player("Tester");
            p.addResources(new int[]{20,20,20,20,20});
            LaunchConfiguration l = new LaunchConfiguration();
            l.setName(p.getName());
            l.setJoinPort(args[1]);
            CatanClient client = new CatanClient(l);
            scanner = new Scanner(new InputStreamReader(System.in));
            
            while (true) {
                String line = scanner.nextLine();
                String[] lineWords = line.split(" ");
                if (lineWords.length == 3) {
                    switch (lineWords[0]) {
                        case "build":
                            switch (lineWords[1]) {
                                case "road":
                                    BuildMove road = new BuildMove(p.getName(), ROAD, Integer.parseInt(lineWords[2]));
                                    client.sendMove(road);
                                    break;
                                case "settlement":
                                    BuildMove settlement = new BuildMove(p.getName(), SETTLEMENT, Integer.parseInt(lineWords[2]));
                                    client.sendMove(settlement);
                                    break;
                                case "city":
                                    BuildMove city = new BuildMove(p.getName(), CITY, Integer.parseInt(lineWords[2]));
                                    client.sendMove(city);
                                    break;
                                default:
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                }
            }
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
    }
}
