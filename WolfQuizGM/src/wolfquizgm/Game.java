/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfquizgm;

import java.util.ArrayList;

/**
 *
 * @author Maxime Billette
 */
public class Game {
    /** Players and Roles available.**/
    
    // A single player can have a maximum of X roles
    public static int maxRole = 2;
    public static int currentRound = 0;
    
    public static ArrayList<Player> players = new ArrayList<Player>();
    public static ArrayList<Role> roles = new ArrayList<Role>();
}
