import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Circle {
    public static ArrayList<Circle> circles = new ArrayList<Circle>();
    private static double randomVelocity = 1; 
    private static double randomMinMass = 20; 
    private static double randomMaxMass = 40; 
    public double radius, mass;
    private Vector2D velocity;
    private Point position;
    public Color color;
    
    private static Random random = new Random();

    public Circle(Point position) {
        this.position = position;
        this.velocity = new Vector2D(random.nextDouble(- Circle.randomVelocity, Circle.randomVelocity), random.nextDouble(- Circle.randomVelocity,Circle.randomVelocity));
        this.mass = random.nextDouble(Circle.randomMinMass, Circle.randomMaxMass);
        this.radius = Math.sqrt(mass);
        this.color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
        Circle.circles.add(this);
    }
    
    public static double getMaxRadius(){
        return Math.sqrt(Circle.randomMaxMass);
    }
    
    public Point getPosition(){
        return this.position;
    }

    public void setPosition(double x, double y) {
        this.position.x = x;
        this.position.y = y; 
    }

    public double getX(){
        return this.position.x;
    }

    public double getY(){
        return this.position.y;
    }
    
    public Vector2D getVelocity() {
        return this.velocity;
    }

    public double getVelocityX(){
        return this.velocity.x;
    }

    public double getVelocityY(){
        return this.velocity.y;
    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }
    
    public void setVelocity(Vector2D vector) {
        this.velocity = vector;
    }
    
    public void setVelocity(double x, double y){
        this.velocity.x = x;
        this.velocity.y = y;
    }

    public String toString(){
        return this.position.x +" ; "+ this.position.y;
    }

    
} 
