/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfquizgm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import static wolfquizgm.WolfQuizGM.commandLoop;

/**
 *
 * @author Maxime Billette
 */
public class Utils {
    public static Player getRandomPlayer(ArrayList<Player> players){
        Random r = new Random();
        
        int index = r.nextInt(players.size());
        Player playerToPick = players.get(index);
        return playerToPick;
    }
    
    public static Role getRandomRole(ArrayList<Role> roles){
        Random r = new Random();
        
        if(!roles.isEmpty()){
            int index = r.nextInt(roles.size());
            Role roleToPick = roles.get(index);
            return roleToPick;
        }
         return null;
    }
    
    public static int getTotalAttributedRoles(){
        int totalAttributedRoles = 0;
        
        for(Player player : Game.players){
            totalAttributedRoles += player.roles.size();
        }
        
        return totalAttributedRoles;
    }
    
    public static Player findPlayer(String name) {
        for(Player player : Game.players) {
            if(name.equals(player.name)){
                return player;
            }
        }
        return null;
    }
    
    public static Role findRole(String name){
        for(Role role : Game.roles) {
            if(name.equals(role.name)){
                return role;
            }
        }
        return null;
    }
    
    public static void defaultPlayers(){
        System.out.println("Place the default players to the game (for tests only)");
        
        // Reset roles
        Game.players = new ArrayList<>();
        
        Player sarah = new Player("Sarah", "Blue");
        Game.players.add(sarah);
        
        Player lea = new Player("Léa", "Blue");
        Game.players.add(lea);
        
        Player perrine = new Player("Perrine", "Blue");
        Game.players.add(perrine);
        
        Player maxime = new Player("Maxime", "Red");
        Game.players.add(maxime);
        
        Player paulLouis = new Player("Paul-Louis", "Red");
        Game.players.add(paulLouis);
        
        Player adrien = new Player("Adrien", "Red");
        Game.players.add(adrien);
        
        Player loulou = new Player("Loulou", "Red");
        Game.players.add(loulou);
        
    }
    
    public static void defaultRoles(){
        System.out.println("Place the default roles to the game");
        
        // Reset roles
        Game.roles = new ArrayList<>();
        
        Role king = new Role("King", "public", 2, 2, 2);
        Game.roles.add(king);

        Role chaman = new Role("Chaman", "public", 0, 2, 1);
        Game.roles.add(chaman);
        
        Role muted = new Role("Muted", "public", 0, 1, 0.3);
        Game.roles.add(muted);
        
        Role doppelganger = new Role("Doppelganger", "public", 0, 1, 0.8);
        Game.roles.add(doppelganger);
        
        Role swapper = new Role("Swapper", "public", 0, 1, 0.6);
        Game.roles.add(swapper);
        
        Role gourmet = new Role("Gourmet", "public", 0, 1, 0.5);
        Game.roles.add(gourmet);
        
        Role bard = new Role("Bard", "public", 0, 2, 0.4);
        Game.roles.add(bard);
        
        Role engineer = new Role("Engineer", "public",  0, 1, 0.2);
        Game.roles.add(engineer);
        
        Role bitch = new Role("Bitch", "private", 0, 2, 0.8);
        Game.roles.add(bitch);
        
        Role madScientist = new Role("Mad Scientist", "private", 0, 1, 0.3);
        Game.roles.add(madScientist);
        
        Role joker = new Role("Joker", "private", 0, 1, 0.3);
        Game.roles.add(joker);
        
        Role assassin = new Role("Assassin", "private", 0, 2, 1);
        Game.roles.add(assassin);
        
        Role angel = new Role("Angel", "private", 1, 2, 1.4);
        Game.roles.add(angel);
        
        Role cheater = new Role("Cheater", "private", 0, 1, 0.2);
        Game.roles.add(cheater);
        
        Role villager = new Role("Villager", "private", 0, 0, 0);
        Game.roles.add(villager);
                
    }
    
    public static void writeLog(String str){
        BufferedWriter bw = null;
        
        try {
         bw = new BufferedWriter(new FileWriter("log.txt", true));
         bw.write(str);
         bw.newLine();
         bw.flush();
         
        } catch (IOException ioe) {
           ioe.printStackTrace();
        } finally {
           if (bw != null) try {
              bw.close();
           } catch (IOException ioe2) {
              // just ignore it
           }
        }
    }
    
}
