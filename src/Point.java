import java.util.Random;

public class Point extends Vector2D{
    
    public Point(double x, double y){
        super(x, y);
    }

    public double distance(Point otherPoint) {
        return Math.sqrt(Math.pow(otherPoint.x - this.x, 2)+ Math.pow(otherPoint.y - this.y, 2));
    }

    public static Point randomPoint(){
        Random random = new Random();
        double maxRadiusOfPoints = Circle.getMaxRadius();
        return new Point(random.nextDouble(maxRadiusOfPoints, App.WIDTH - maxRadiusOfPoints), random.nextDouble(maxRadiusOfPoints, App.HEIGHT - maxRadiusOfPoints));
    }

    public String toString(){
        return this.x+ " ; "+this.y; 
    }
}
