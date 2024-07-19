import java.util.ArrayList;
/**
 * Cette Classe utilise un arbre quaternaire (QuadTree) pour gérer les collisions entre les cercles.
 * <p> Elle gère aussi la collision avec le mur et le déplacement des cercles
 */

public class CirclePhysics {
    public static Quadtree quadtree = new Quadtree(new Rectangle(0,0, App.canvas.getWidth(), App.canvas.getHeight()));
    private static ArrayList<Circle> circles = new ArrayList<>();

    public static void applyPhysics(){    
        circles = new ArrayList<>(Circle.circles);
        CirclePhysics.quadtree = createQuadtree();
        for (Circle circle : circles) {   
            updateCirclesPosition(circle);
            handleCollisionWithWall(circle);
            handleCollisionWithCircles(circle);
        }
    }

    private static Quadtree createQuadtree(){
        Quadtree quadtree = new Quadtree(new Rectangle(0,0, App.canvas.getWidth(), App.canvas.getHeight()));
        for (Circle circle : circles) {
            quadtree.add(circle);
        }
        return quadtree;
    }

    private static void updateCirclesPosition(Circle circle){
        double newX = circle.getX()+ (circle.getVelocity().x);
        double newY = circle.getY()+ (circle.getVelocity().y);
        circle.setPosition(newX,newY);
    }

    private static void handleCollisionWithWall(Circle circle){
        boolean collisionOnLeftWall = (circle.getX() < 0 + circle.radius);
        boolean collisionOnRightWall = (circle.getX() > App.canvas.getWidth() - circle.radius);
        boolean collisionOnTopWall = (circle.getY() < 0 + circle.radius);
        boolean collisionOnBottomWall = (circle.getY() > App.canvas.getHeight() - circle.radius);

        if (collisionOnLeftWall){
            circle.setPosition(circle.radius, circle.getY());
            circle.setVelocity(circle.getVelocityX() * -1, circle.getVelocityY());
        }
        if (collisionOnRightWall) {
            circle.setPosition(App.canvas.getWidth() - circle.radius, circle.getY());
            circle.setVelocity(circle.getVelocityX() * -1, circle.getVelocityY());
        }
        if (collisionOnTopWall){
            circle.setPosition(circle.getX(), circle.radius);   
            circle.setVelocity(circle.getVelocityX(), circle.getVelocityY()* -1);

        } 
        else if(collisionOnBottomWall) {
            circle.setPosition(circle.getX(), App.canvas.getHeight() - circle.radius);            
            circle.setVelocity(circle.getVelocityX(), circle.getVelocityY()* -1);
        }
    };
    
    private static void handleCollisionWithCircles(Circle circle){

        ArrayList<Circle> closestCircles = quadtree.findClosestCirclesOf(circle);

        ArrayList<Circle> collidingCircles = collisionDetectionBetween(circle, closestCircles);

        for (Circle otherCircle : collidingCircles) {
            collisionResolutionBetween(circle, otherCircle);
        }
    }     

    private static ArrayList<Circle> collisionDetectionBetween(Circle circle, ArrayList<Circle> closestCircles){
        ArrayList<Circle> collidingCircles = new ArrayList<>();

        for (Circle otherCircle : closestCircles) {
            if (otherCircle != circle && otherCircle != null) {
                double distance = circle.getPosition().distance(otherCircle.getPosition());
                if (distance < circle.radius + otherCircle.radius) {
                    collidingCircles.add(otherCircle);
                }
            }
        }

        return collidingCircles;
    }

    private static void collisionResolutionBetween(Circle circle, Circle otherCircle){
        preventOverlapping(circle, otherCircle);
        Vector2D deltaVelocityCircle = computeDeltaVelocity(circle, otherCircle);
        Vector2D deltaVelocityOtherCircle = computeDeltaVelocity(otherCircle, circle);
        circle.getVelocity().addVector(deltaVelocityCircle);
        otherCircle.getVelocity().addVector(deltaVelocityOtherCircle);    
    }

    private static void preventOverlapping(Circle circle, Circle otherCircle){
        // Repousse chaque cercle de la moitié de la distance de superposition en suivant la ligne d'impact
        Vector2D impactLine = Vector2D.substractVectors(otherCircle.getPosition(), circle.getPosition());
        double distanceBetweenCircles = circle.getPosition().distance(otherCircle.getPosition());
        double distanceOfOverlaping = distanceBetweenCircles - (circle.getRadius() + otherCircle.getRadius());
        Vector2D direction = new Vector2D(impactLine);
        direction.setMagnitude(distanceOfOverlaping * 0.5);
        circle.getPosition().addVector(direction);
        otherCircle.getPosition().substractVector(direction);
    }

    private static Vector2D computeDeltaVelocity(Circle circle, Circle otherCircle){
        // calcul de la différence entre la nouvelle vitesse et l'ancienne en utilisant cette formule : 
        // https://wikimedia.org/api/rest_v1/media/math/render/svg/14d5feb68844edae9e31c9cb4a2197ee922e409c
        
        Vector2D impactLine = Vector2D.substractVectors(otherCircle.getPosition(), circle.getPosition());
        Vector2D velocityDifference = Vector2D.substractVectors(otherCircle.getVelocity(), circle.getVelocity());
        double distance = circle.getPosition().distance(otherCircle.getPosition());
        double massSum = circle.getMass() + otherCircle.getMass();
        double numerator = 2 * otherCircle.getMass() * velocityDifference.dotProduct(impactLine);
        double denumerator = massSum * distance * distance ;
        Vector2D deltaVelocity = new Vector2D(impactLine);
        deltaVelocity.dotProduct(numerator/denumerator);
        return deltaVelocity;
    }

}
