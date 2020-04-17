package wolfquizgm;

import java.util.Scanner;
import java.io.*;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

/**
 *
 * @author Maxime Billette
 */
public class WolfQuizGM {
    
    public static void commandLoop(BufferedWriter bw){
        
        Scanner in = new Scanner(System.in);
        String input = "";
        String[] arguments = input.split(" ");
        
        while(!arguments[0].equals("exit")) {
            System.out.print("> ");
            input = in.nextLine();
            arguments = input.split(" ");
            
            Utils.writeLog("> " + input);
            
            String commandToPerform = arguments[0];
            
            switch(commandToPerform){
                
                case "h":
                case "help":
                    Commands.help();
                    break;
                
                case "q":
                case "quit":  
                case "exit":
                    break;
                    
                // add the default roles to the game
                case "dr":
                case "default-roles":
                    Utils.defaultRoles();
                    break;
                    
                // add the default players to the game (for tests only)
                case "dp":
                case "default-players":
                    Utils.defaultPlayers();
                    break;
                
                case "p":
                case "player":
                    Commands.player(arguments);
                    break;
                
                case "r":
                case "role":
                    Commands.role(arguments);
                    break;
                
                case "s":
                case "show":
                    Commands.show(arguments);
                    break;
                
                case "t":    
                case "team":    
                    Commands.team(arguments);
                    break;
                    
                 /** Roll the roles to start a new fresh round **/
                case "n":
                case "next-round":
                case "next":
                    Commands.nextRound();
                    break;
                    
                default:
                    System.out.println("Unknown command '" + commandToPerform + "'");
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        BufferedWriter bw = null;
    
        System.out.println("** Welcome to the WolfQuiz GameMaster Command bash **");
        System.out.println("Start your inputs here, use help command if needed (type 'help')");
        
        try {
         bw = new BufferedWriter(new FileWriter("log.txt", true));
         bw.write("-- Beginning of Game ---");
         bw.newLine();
         bw.flush();
         
         /** Start the Command Loop **/
         commandLoop(bw);
	 
         bw.write("-- Ending of Game ---");
         bw.newLine();
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
