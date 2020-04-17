package wolfquizgm;

/**
 *
 * @author Maxime Billette
 */
public class Role {
    
    public String name;
    public String visibility;
    
    public int minNumber;
    public int maxNumber;
    
    // desired mean value for the Gaussian random distribution
    public double mean;
    
    public Role(String _name, String _visibility, int _minNumber, int _maxNumber, double _mean){
        name = _name;
        visibility = _visibility;
        minNumber = _minNumber;
        maxNumber = _maxNumber;
        mean = _mean;
    }
}
