package wolfquizgm;

import java.util.ArrayList;

/**
 *
 * @author Maxime Billette
 */
public class Player {
    public String name;
    public String team;
    public int points;
    public ArrayList<Role> roles;
    
    public Player(String _name, String _team){
        name = _name;
        team = _team;
        points = 0;
        roles = new ArrayList<>();
    }
}
