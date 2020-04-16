/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfquizgm;

import java.io.*;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.ArrayList;

/**
 *
 * @author Maxime Billette
 */
public class Commands {
    public static void help(){
        System.out.println("You need help !");
    }
    
    public static void player(String[] arguments){
        String commandToPerform = "";
        
        if(arguments.length > 1)
            commandToPerform = arguments[1];

        switch(commandToPerform){
            case "add":
            case "create":
                addPlayer(arguments);
                break;
            case "show":
                showPlayers();
                break;
            default:
                System.out.println("Bad use of the '" + arguments[0] + "' command");
        }
    }
    
    
    public static void addPlayer(String[] arguments){
        System.out.println("Add a player");
    }
    
    public static void removePlayer(String[] arguments){
        System.out.println("Remove a player");
    }
    
    public static void showPlayers(){
        System.out.println("Show the current players");
        System.out.println("---");
        for (Player player : Game.players) {
            System.out.println("Name : " + player.name );
            System.out.println("Team: " + player.team );
            System.out.println("");
        }  
        System.out.println("---");
    }
    
    public static void defaultPlayers(){
        System.out.println("Place the default players to the game (for tests only)");
        
        // Reset roles
        Game.players = new ArrayList<>();
        
        Player sarah = new Player("Sarah", "Blue");
        Game.players.add(sarah);
        
        Player lea = new Player("Léa", "Blue");
        Game.players.add(lea);
        
        Player maxime = new Player("Maxime", "Red");
        Game.players.add(maxime);
        
        Player paulLouis = new Player("Paul-Louis", "Red");
        Game.players.add(paulLouis);
        
    }
    
    
    public static void role(String[] arguments){
        String commandToPerform = "";
        
        if(arguments.length > 1)
            commandToPerform = arguments[1];

        switch(commandToPerform){
            case "add":
            case "create":
                addRole(arguments);
                break;
            case "show":
                showRoles();
                break;
            default:
                System.out.println("Bad use of the '" + arguments[0] + "' command");
        }
    }
    
    public static void addRole(String[] arguments){
        System.out.println("Add a role");
    }
    
    public static void showRoles(){
        System.out.println("Show the available roles :");
        System.out.println("---");
        for (Role role : Game.roles) {
            System.out.println("Name : " + role.name );
            System.out.println("Is public ? : " + role.isPublic );
            System.out.println("");
        }  
        System.out.println("---");
    }
    
    
    public static void defaultRoles(){
        System.out.println("Place the default roles to the game");
        
        // Reset roles
        Game.roles = new ArrayList<>();
        
        Role king = new Role("King", true);
        Game.roles.add(king);

        Role chaman = new Role("Chaman", true);
        Game.roles.add(chaman);
        
        Role muted = new Role("Muted", true);
        Game.roles.add(muted);
        
        Role doppelganger = new Role("Doppelganger", true);
        Game.roles.add(doppelganger);
        
        Role swapper = new Role("Swapper", true);
        Game.roles.add(swapper);
        
        Role gourmet = new Role("Gourmet", true);
        Game.roles.add(gourmet);
        
        Role bard = new Role("Bard", true);
        Game.roles.add(bard);
        
        Role engineer = new Role("Engineer", true);
        Game.roles.add(engineer);
        
        Role bitch = new Role("Bitch", false);
        Game.roles.add(bitch);
        
        Role madScientist = new Role("Mad Scientist", false);
        Game.roles.add(madScientist);
        
        Role joker = new Role("Joker", false);
        Game.roles.add(joker);
        
        Role assassin = new Role("Assassin", false);
        Game.roles.add(assassin);
        
        Role angel = new Role("Angel", false);
        Game.roles.add(angel);
        
        Role cheater = new Role("Cheater", false);
        Game.roles.add(cheater);
        
        Role villager = new Role("Villager", false);
        Game.roles.add(villager);
                
    }
}
