
public class City {
    double x;
    double y;
    
    // Constructs a randomly placed city
    public City(){
        this.x = (int)(Math.random()*200);
        this.y = (int)(Math.random()*200);
    }
    
    // Constructs a city at chosen x, y location
    public City(double d, double e){
        this.x = d;
        this.y = e;
    }
    
    // Gets city's x coordinate
    public double getX(){
        return this.x;
    }
    
    // Gets city's y coordinate
    public double getY(){
        return this.y;
    }
    
    // Gets the distance to given city
    public double distanceTo(City city){
        double xDistance = Math.abs(getX() - city.getX());
        double yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
        
        return distance;
    }
    
    @Override
    public String toString(){
        return getX()+", "+getY();
    }
}