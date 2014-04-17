/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.atreil.catan.networking.client;

import edu.brown.cs032.tmercuri.catan.logic.Player;
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
            CatanClient client = new CatanClient(p, args[0], Integer.parseInt(args[1]));
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            
            while (true) {
                String line = scanner.nextLine();
                String[] lineWords = line.split(" ");
                if (lineWords.length == 3) {
                    switch (lineWords[0]) {
                        case "build":
                            switch (lineWords[1]) {
                                case "road":
                                    
                                    break;
                                case "settlement":
                                    
                                    break;
                                case "city":
                                    
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
