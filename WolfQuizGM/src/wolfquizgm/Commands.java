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
import java.util.Random;

/**
 *
 * @author Maxime Billette
 */
public class Commands {
    
    /** ================ MAIN MENU OPTIONS ============ **/
    public static void help(){
        System.out.println("Help menu coming soon !");
    }
    
    public static void show(String[] arguments){
        String commandToPerform = "";
        
        if(arguments.length > 1)
            commandToPerform = arguments[1];

        switch(commandToPerform){
            case "p":
            case "players":
                showPlayers();
                break;
            case "r":
            case "roles":
                showRoles();
                break;
            case "public":
                showPublic();
                break;
            case "private":
                showPrivate();
                break;
            default:
                System.out.println("Bad use of the '" + arguments[0] + "' command");
        }
    }
    
    
     /** ================ NEXT ROUND ============ **/
    
    /** Roll the roles and start a new round **/
    public static void nextRound() {
        System.out.println("Starting a new round !");
        Game.currentRound++;
        System.out.println("Round " + Game.currentRound);
        
        if( (Game.currentRound - 1) > 0){
            Utils.writeLog("");
            Utils.writeLog(" /=================================/");
            Utils.writeLog("Ending of Round " + (Game.currentRound - 1));
            writeInfos();
        }
        
        /** Roles of players are refreshed **/
        for(Player player: Game.players){
            player.roles = new ArrayList<>();
        }
        
        /** For each role, we determine the number of instances of this role
         * that have to be distributed. **/
        
        ArrayList<Role> roles = Game.roles;
        ArrayList<Role> rolesToDistribute = new ArrayList<>();
        
        for(Role role: roles){
            // create random object 
            Random r = new Random();
        
            int minNumber = role.minNumber;
            int maxNumber = role.maxNumber;
            double mean = role.mean;
            int randomNumber = (int) Math.round((r.nextGaussian() + mean));
            if(randomNumber < minNumber)
                randomNumber = minNumber;
            if(randomNumber > maxNumber)
                randomNumber = maxNumber;

            System.out.println("Number of '" + role.name + "' to distribute: " + randomNumber);
            for(int i=0; i< randomNumber; i++){
                rolesToDistribute.add(role);
            }
        }
        
        int totalRolesToDistribute = rolesToDistribute.size();
        System.out.println("Total of roles to be distributed : " + totalRolesToDistribute);
        System.out.println();
        
        /** For each role to be distributed, we attribute it to some player,
         * with respect to the rules. **/
        int totalAttributedRoles = Utils.getTotalAttributedRoles();
        int maxAttributedRoles = Utils.getMaxAttributedRoles();
        
        while((!rolesToDistribute.isEmpty()) && (totalAttributedRoles < maxAttributedRoles) ){
            Role role = Utils.getRandomRole(rolesToDistribute);
            // We pick a random player
            Player player = Utils.getRandomPlayer(Game.players);
            // If there is enough remaining place for a role
            if(player.roles.size() < Game.maxRole){
                // If the player does not already have this role
                if(!player.roles.contains(role)){
                    
                    boolean noOtherKing = true;
                    // 1 King per team maximum
                    if("King".equals(role.name)){
                        for(Player player2: Game.players){
                            if(player2.team.equals(player.team) && player2.roles.contains(role)){
                                noOtherKing = false;
                            }
                        }
                    }
                    
                    if(noOtherKing){
                        player.roles.add(role);
                        rolesToDistribute.remove(role);
                        System.out.println("Player '" + player.name + "' has now the role : '" + role.name + "'");
                    }
                    
                    totalAttributedRoles = Utils.getTotalAttributedRoles();
                }
            }
        }
        System.out.println();
        
        // Give the 'Villager' role to those without any role
        for(Player player: Game.players){
            if(player.roles.isEmpty()){
                Role villager = Utils.findRole("Villager");
                player.roles.add(villager);
            }
        }
        
        System.out.println("Players with their roles : ");
        showPlayers();
        
        Utils.writeLog("");
        Utils.writeLog(" /=================================/");
        Utils.writeLog("Beginning of Round " + (Game.currentRound));
        writeInfos();
        
    }
            
    
    /** ================ PLAYER ============ **/
    
    public static void player(String[] arguments){
        String commandToPerform = "";
        
        if(arguments.length > 1)
            commandToPerform = arguments[1];

        switch(commandToPerform){
            case "+":
            case "add":
            case "create":
                addPlayer(arguments);
                break;
            
            case "-":
            case "remove":
            case "delete":
                removePlayer(arguments);
                break;
            
            case "set":
            case "points":
                setPoints(arguments);
                break;
            
            case "s":
            case "show":
                showPlayers();
                break;
            default:
                System.out.println("Bad use of the '" + arguments[0] + "' command");
        }
    }
    
    
    public static void addPlayer(String[] arguments){
        System.out.println("Add a player to the game");
        String name = "";
        String team = "";
        
        if(arguments.length > 3){
            name = arguments[2];
            team = arguments[3];
            if(! (name.equals("") &&  team.equals(""))) {
                Player playerToAdd = new Player(name, team);
                Game.players.add(playerToAdd);
                System.out.println("Player '" + name + "' of Team " + team + " has been added");
            }
        } else {
            System.out.println("Bad use of the '" + arguments[1] + "' command");
        }
        
    }
    
    public static void removePlayer(String[] arguments){
        System.out.println("Remove a player from the game");
        String name = "";
        
        if(arguments.length > 2){
            name = arguments[2];
            if(! (name.equals(""))) {
                Player playerToRemove = Utils.findPlayer(name);
                Game.players.remove(playerToRemove);
                System.out.println("Player '" + name + "' has been removed ");
            }
        } else {
            System.out.println("Bad use of the '" + arguments[1] + "' command");
        }
    }
    
    public static void showPlayers(){
        System.out.println("Show the current players");
        System.out.println("---");
        for (Player player : Game.players) {
            System.out.println("Name : " + player.name );
            System.out.println("Points : " + player.points );
            System.out.println("Team : " + player.team );
            for(Role role: player.roles){
                System.out.println("Role : " + role.name );
            }
            System.out.println("");
        }  
        System.out.println("---");
    }
    
    public static void writeInfos(){
        Utils.writeLog("Informations :");
        Utils.writeLog("---");
        for (Player player : Game.players) {
            Utils.writeLog("Name : " + player.name );
            Utils.writeLog("Points : " + player.points );
            Utils.writeLog("Team : " + player.team );
            for(Role role: player.roles){
                Utils.writeLog("Role : " + role.name );
            }
            Utils.writeLog("");
        }  
        Utils.writeLog("---");
    }
    
    public static void showPublic(){
        System.out.println("Show the current players with Public roles");
        System.out.println("---");
        for (Player player : Game.players) {
            System.out.println("Name : " + player.name );
            System.out.println("Team : " + player.team );
            for(Role role: player.roles){
                if("public".equals(role.visibility)){
                    System.out.println("Role : " + role.name );
                }
            }
            System.out.println("");
        }  
        System.out.println("---");
    }
    
    public static void showPrivate(){
        System.out.println("Show the current players with Private roles");
        System.out.println("---");
        for (Player player : Game.players) {
            System.out.println("Name : " + player.name );
            System.out.println("Team : " + player.team );
            for(Role role: player.roles){
                if("private".equals(role.visibility)){
                    System.out.println("Role : " + role.name );
                }
            }
            System.out.println("");
        }  
        System.out.println("---");
    }
    
    public static void setPoints(String[] arguments){
        System.out.println("set the points of a targeted player");
        String name = "";
        String operator = "";
        int amount = 0;
        
        if(arguments.length > 4){
            name = arguments[2];
            operator = arguments[3];
            try {
                amount = Integer.parseInt(arguments[4]);
            } catch(NumberFormatException nfe) {
                
            }
            
            if(! (name.equals("") &&  operator.equals(""))) {
                switch(operator){
                    case "+":
                        addPoints(name, amount);
                        break;
                    case "-":
                        removePoints(name, amount);
                        break;
                    case "=":
                        fixPoints(name, amount);
                        break;
                    default:
                        break;
                }
                
            }
        } else {
            System.out.println("Bad use of the '" + arguments[1] + "' command");
        }
    }
    
    /** ================ PLAYER POINTS ============== **/
    
    public static void addPoints(String name, int amount){
        //System.out.println("add the points of a targeted player");
        Player player = Utils.findPlayer(name);
        if(player != null) {
            player.points += amount;
        }
    }
    public static void removePoints(String name, int amount){
        //System.out.println("remove the points of a targeted player");
        Player player = Utils.findPlayer(name);
        if(player != null) {
            player.points -= amount;
        }
    }
    public static void fixPoints(String name, int amount){
        //System.out.println("fix the points of a targeted player");
        Player player = Utils.findPlayer(name);
        if(player != null) {
            player.points = amount;
        }
    }
    
    // Swap 2 people's team. Power of the Swapper
    public static void swapTeam(String[] arguments){
        if(arguments.length > 2){
            String name1 = arguments[1];
            String name2 = arguments[2];
            Player player1 = Utils.findPlayer(name1);
            Player player2 = Utils.findPlayer(name2);

            if(player1 != null && player2 != null){
                String tmp = player1.team;
                player1.team = player2.team;
                player2.team = tmp;
                System.out.println("Team of '" + name1 + "' and '" + name2 + "' have been swapped");
            }
        } else {
            System.out.println("Bad use of the '" + arguments[0] + "' command");
        }
        
    }
    
   
    /** ================ ROLES ============ **/
    
    public static void role(String[] arguments){
        String commandToPerform = "";
        
        if(arguments.length > 1)
            commandToPerform = arguments[1];

        switch(commandToPerform){
            
            case "+":
            case "add":
            case "create":
                addRole(arguments);
                break;
            
            case "-":
            case "remove":
            case "delete":
                removeRole(arguments);
                break;
            
            case "change":
            case "modify":
            case "m":
                modifyRole(arguments);
                break;
            
            case "s":
            case "show":
                showRoles();
                break;
            default:
                System.out.println("Bad use of the '" + arguments[0] + "' command");
        }
    }
    
    public static Role findRole(String name) {
        for(Role role : Game.roles) {
            if(name.equals(role.name)){
                return role;
            }
        }
        return null;
    }
    
    public static void addRole(String[] arguments){
        System.out.println("Add a role");
        String name = "";
        String visibility = "";
        int minNumber = 0;
        int maxNumber = 0;
        double mean = 0.0;
        
        if(arguments.length > 6){
            name = arguments[2];
            visibility = arguments[3];
            minNumber = Integer.parseInt(arguments[4]);
            maxNumber = Integer.parseInt(arguments[5]);
            mean = Double.parseDouble(arguments[6]);
            
            if(! (name.equals("") &&  visibility.equals(""))) {
                Role roleToAdd = new Role(name, visibility, minNumber, maxNumber, mean);
                Game.roles.add(roleToAdd);
                System.out.println("Role '" + name + "' with visibility " + visibility + " has been added");
                System.out.println("This role has a minimum number of " + minNumber + ", a maximum of " + maxNumber 
                        + " and a mean of " + mean);
            }
        } else {
            System.out.println("Bad use of the '" + arguments[1] + "' command");
        }
    }
    
    public static void modifyRole(String[] arguments){
        System.out.println("Modify a role");
        String name = "";
        int minNumber = 0;
        int maxNumber = 0;
        double mean = 0.0;
        
        if(arguments.length > 5){
            name = arguments[2];
            minNumber = Integer.parseInt(arguments[3]);
            maxNumber = Integer.parseInt(arguments[4]);
            mean = Double.parseDouble(arguments[5]);
            
            if( !(name.equals("")) ) {
                Role roleToModify = Utils.findRole(name);
                
                roleToModify.minNumber = minNumber;
                roleToModify.maxNumber = maxNumber;
                roleToModify.mean = mean;
                
                System.out.println("Role '" + name + "' has been modified");
                System.out.println("This role has now a minimum number of " + minNumber + ", a maximum of " + maxNumber 
                        + " and a mean of " + mean);
            }
        } else {
            System.out.println("Bad use of the '" + arguments[1] + "' command");
        }
    }
    
    public static void removeRole(String[] arguments){
        System.out.println("Remove a role from the game");
        String name = "";
        
        if(arguments.length > 2){
            name = arguments[2];
            if(! (name.equals(""))) {
                Role roleToRemove = findRole(name);
                Game.roles.remove(roleToRemove);
                System.out.println("Role '" + name + "' has been removed ");
            }
        } else {
            System.out.println("Bad use of the '" + arguments[1] + "' command");
        }
    }
    
    public static void showRoles(){
        System.out.println("Show the available roles :");
        System.out.println("---");
        for (Role role : Game.roles) {
            System.out.println("Name : " + role.name );
            System.out.println("Visibility : " + role.visibility );
            System.out.println("Minimum number : " + role.minNumber );
            System.out.println("Maximum number : " + role.maxNumber );
            System.out.println("Mean : " + role.mean );
            System.out.println("");
        }  
        System.out.println("---");
    }
    
    /** ================ TEAM ============ **/
    
    public static void team(String[] arguments){
        String teamName = "";
        String commandToPerform = "";
        
        if(arguments.length > 2){
            teamName = arguments[1];
            commandToPerform = arguments[2];
        }

        switch(commandToPerform){
                
            case "w":
            case "win":
            case "wins":
                teamWins(teamName);
                break;
                
            default:
                System.out.println("Bad use of the '" + arguments[0] + "' command");
        }
    }
    
    public static void teamWins(String teamName){
        System.out.println("Team '" + teamName + "' wins this round !");
        for(Player player : Game.players){
            if(teamName.equals(player.team)){
                
                // If not the Bitch
                Role bitch = findRole("Bitch");
                if(!player.roles.contains(bitch)){
                    // If not the Villager
                    Role villager = findRole("Villager");
                    if(!player.roles.contains(villager)) {
                        addPoints(player.name, 1);
                    } else {
                        // If the villager
                        addPoints(player.name, 2);
                    }
                }
            }
        }
    }
}
