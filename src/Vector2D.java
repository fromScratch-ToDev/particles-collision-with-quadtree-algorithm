public class Vector2D {
    public double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D vector){
        this.x = vector.x;
        this.y = vector.y;
    }

    public static Vector2D addVectors(Vector2D firstVector, Vector2D secondVector){
        return new Vector2D(firstVector.x + secondVector.x, 
                            firstVector.y + secondVector.y);
    }
    public static Vector2D substractVectors(Vector2D firstVector, Vector2D secondVector){
        return new Vector2D(firstVector.x - secondVector.x, 
                            firstVector.y - secondVector.y);
    }

    public double dotProduct(Vector2D otherVector){
       return (this.x * otherVector.x) + (this.y * otherVector.y);
    }

    public void dotProduct(double scalar){
        this.x *= scalar;  
        this.y *= scalar;  
    }

    public void addVector(Vector2D otherVector){
        this.x += otherVector.x;
        this.y += otherVector.y;
    }
    
    public void substractVector(Vector2D otherVector){
        this.x -= otherVector.x;
        this.y -= otherVector.y;
    }
    
    public double getMagnitude() {
        return Math.sqrt(Math.pow(x, 2)+Math.pow(y,2));
    }
    
    public void setMagnitude(double magnitude) {
        double coefficient = magnitude / this.getMagnitude();
        this.x = x *coefficient; 
        this.y = y *coefficient; 
    }

    public String toString(){
        return "composante X : "+this.x + "\ncomposante Y : "+this.y;
    }
}
