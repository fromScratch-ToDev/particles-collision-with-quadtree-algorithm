import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CanvasCircle extends Canvas {
    
	private static final long serialVersionUID = 1L;

    private static ArrayList<Circle> circles = new ArrayList<>();
    static boolean isQuadtreeVisible = false;

	public CanvasCircle(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(applyPhysicsAndRepaint(), 0, 1000 / 60); // 60 FPS
    }
    
 

    private TimerTask applyPhysicsAndRepaint(){
        return new TimerTask() {
            @Override
            public void run() {
                App.jframe.setTitle("Nombre de cercles : "+Circle.circles.size());
                circles = new ArrayList<>(Circle.circles);
                CirclePhysics.applyPhysics();
                repaint(); // appelle la méthode update, fonctionnement par défaut de la classe Canvas
            }
        };
    }
    

    @Override
    public void update(Graphics g){
        paint(g);
    }
    
    @Override
    public void paint(Graphics g) {
        BufferedImage backgroundBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics graphics = backgroundBuffer.createGraphics();
        graphics = drawOnTheGraphics(graphics);
        g.drawImage(backgroundBuffer, 0, 0, null);
    }
    
    private Graphics drawOnTheGraphics(Graphics graphic){
        drawCircles(graphic);

        if(isQuadtreeVisible){
            drawQuadtree(graphic, CirclePhysics.quadtree);
        }  

        return graphic;
    }

    private void drawCircles(Graphics graphic){
        for (Circle circle : circles) {
            graphic.setColor(circle.color);
            int x = (int) (circle.getX() - circle.getRadius());
            int y = (int) (circle.getY() - circle.getRadius());
            int diameter = (int) (2*circle.getRadius());
            graphic.fillOval(x,y,diameter,diameter);
        }
    }

    private void drawQuadtree(Graphics graphic, Quadtree quadtree){
        if (quadtree.isDivided){
            drawQuadtree(graphic, quadtree.topLeftChild);
            drawQuadtree(graphic, quadtree.topRightChild);
            drawQuadtree(graphic, quadtree.bottomLeftChild);
            drawQuadtree(graphic, quadtree.bottomRightChild);
        }
        else{
            graphic.setColor(Color.WHITE);
            graphic.drawRect((int)quadtree.boundary.x, (int)quadtree.boundary.y, (int)quadtree.boundary.width, (int)quadtree.boundary.height);
        }
    }
}
    