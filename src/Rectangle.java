public class Rectangle {
    public double x, y, width, height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(Circle circle){
        /* author : https://www.jeffreythompson.org/collision-detection/circle-rect.php */
    	
        // temporary variables to set edges for testing
        double testX = circle.getX();
        double testY = circle.getY();

        // which edge is closest?
        if (circle.getX() < this.x) testX = this.x;      // test left edge
        else if (circle.getX() > this.x+this.width) testX = this.x+this.width;   // right edge
        if (circle.getY() < this.y) testY = this.y;      // top edge
        else if (circle.getY() > this.y+this.height) testY = this.y+this.height;   // bottom edge
    	
        // get distance from closest edges
        double distX = circle.getX()-testX;
        double distY = circle.getY()-testY;
        double distance = Math.sqrt( (distX*distX) + (distY*distY) );

        // if the distance is less than the radius, collision!
        if (distance <= circle.radius) {
            return true;
        }
        return false;
    }
}
