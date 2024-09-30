import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JFrame;
 
public class App{   

    static int WIDTH = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    static int HEIGHT = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() -40;
    static CanvasCircle canvas = new CanvasCircle();
    static JFrame jframe = new JFrame();
    private static int numberOfCircles = 0;

    public static void main(String[] args) {
        configureFrame();
        configureCanvas();
        initializeApp();
    }
    
    private static void configureFrame(){
        jframe.setSize(WIDTH, HEIGHT);
        jframe.add(canvas);
        jframe.setVisible(true);
    }

    private static void configureCanvas(){
  
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                for (int i = 0; i < 5; i++) {
                    Point clickPoint = new Point(e.getX(), e.getY());
                    new Circle(clickPoint);
                }
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                new Circle(new Point(e.getX(), e.getY()));
            }
            
        });

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'f':
                        CanvasCircle.isQuadtreeVisible = !CanvasCircle.isQuadtreeVisible;
                        break;

                    case 'r':
                        Circle.circles = new ArrayList<>();
                        break;
                
                    case '+':
                        Circle.setMass(Circle.mass *= 2);
                        break;

                    case '-':
                        Circle.setMass(Circle.mass /= 2);
                        if (Circle.mass <= 1) {
                            Circle.setMass(1);
                        }
                        break;

                    default:
                        break;
                }
            } 
        });            
    }

   

    private static void initializeApp(){
        for (int i = 0; i < numberOfCircles; i++) {
            new Circle(Point.randomPoint());
        }
    
    }
}
