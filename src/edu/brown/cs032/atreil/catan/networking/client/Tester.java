/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.atreil.catan.networking.client;

import edu.brown.cs032.eheimark.catan.menu.LaunchConfiguration;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import static edu.brown.cs032.tmercuri.catan.logic.move.BuildConstants.CITY;
import static edu.brown.cs032.tmercuri.catan.logic.move.BuildConstants.ROAD;
import static edu.brown.cs032.tmercuri.catan.logic.move.BuildConstants.SETTLEMENT;
import edu.brown.cs032.tmercuri.catan.logic.move.BuildMove;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author Thomas Mercurio
 */
public class Tester {
    
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
            l.setAvatarName(p.getName());
            l.setJoinPort(Integer.parseInt(args[1]));
            CatanClient client = new CatanClient(l);
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            
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
                                    System.out.println(client.readServerMessage());
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
                                    System.out.println("What?");
                                    break;
                            }
                            break;
                        default:
                            System.out.println("What?");
                            break;
                    }
                } else {
                    System.out.println("What?");
                }
            }
            
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
    }
}
