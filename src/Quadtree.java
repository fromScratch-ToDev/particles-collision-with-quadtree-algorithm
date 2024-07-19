import java.util.ArrayList;
/**
 * Un arbre quaternaire est une structure de donnée qui permet d'optimiser la recherche d'un objet dans un espace en divisant cette espace en sous espace
 * <p> https://fr.wikipedia.org/wiki/Quadtree#:~:text=Un%20quadtree%20ou%20arbre%20quaternaire,subdivisant%20r%C3%A9cursivement%20en%20quatre%20n%C5%93uds.
 */
public class Quadtree {
    public Rectangle boundary;
    public boolean isDivided;
    public ArrayList<Circle> containedCircles = new ArrayList<Circle>();
    private int capacity = 2;
    public Quadtree topLeftChild;
    public Quadtree topRightChild;
    public Quadtree bottomLeftChild;
    public Quadtree bottomRightChild;

    public Quadtree(Rectangle boundary) {
        this.boundary = boundary;
        this.isDivided = false;
    }

    public void add(Circle circle){
        if(! boundary.contains(circle)){
            return;
        }
        if (!isDivided){
            insert(circle);
        }
        if (isDivided){
            topLeftChild.add(circle);
            topRightChild.add(circle);
            bottomLeftChild.add(circle);
            bottomRightChild.add(circle);
        }
        
    }

    private void insert(Circle circle){
        if (containedCircles.size() < capacity){
            containedCircles.add(circle);
        }
        else{
            subdivise();
            
            for (Circle containeCircle : containedCircles) {
                this.add(containeCircle);  
            }
        }
    }

    private void subdivise(){
        double width = this.boundary.width / 2;
        double height = this.boundary.height / 2;

        Rectangle topLeftRectangle = new Rectangle(this.boundary.x, this.boundary.y, width, height);
        Rectangle topRightRectangle = new Rectangle(this.boundary.x + width, this.boundary.y, width, height);
        Rectangle bottomLeftRectangle = new Rectangle(this.boundary.x, this.boundary.y + height, width, height);
        Rectangle bottomRightRectangle = new Rectangle(this.boundary.x + width, this.boundary.y + height, width, height);
        
        topLeftChild =  new Quadtree(topLeftRectangle);
        topRightChild =  new Quadtree(topRightRectangle);
        bottomLeftChild =  new Quadtree(bottomLeftRectangle);
        bottomRightChild =  new Quadtree(bottomRightRectangle);
        
        // On double la capacité pour éviter une récursion infinie 
        // lorsque, pour une raison quelconque, les cerlces se superposent
        topLeftChild.capacity  = this.capacity * 2;
        topRightChild.capacity  = this.capacity * 2;
        bottomLeftChild.capacity  = this.capacity * 2;
        bottomRightChild.capacity  = this.capacity * 2;

        this.isDivided = true;
    }


    public ArrayList<Circle> findClosestCirclesOf(Circle circle){
        ArrayList<Circle> nearestCircles = new ArrayList<Circle>();
        if (! boundary.contains(circle)) {
            return nearestCircles;
        }
        else if (isDivided){
            nearestCircles.addAll(topLeftChild.findClosestCirclesOf(circle));
            nearestCircles.addAll(topRightChild.findClosestCirclesOf(circle));
            nearestCircles.addAll(bottomLeftChild.findClosestCirclesOf(circle));
            nearestCircles.addAll(bottomRightChild.findClosestCirclesOf(circle));
        }
        else if (this.containedCircles.contains(circle)){
            nearestCircles.addAll(containedCircles);
        }
        return nearestCircles;
    }

}
 