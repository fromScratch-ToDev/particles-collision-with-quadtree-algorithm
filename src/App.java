import java.awt.event.*;

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

        // Adding a window listener to close the application
        jframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        
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
    }

   

    private static void initializeApp(){
        for (int i = 0; i < numberOfCircles; i++) {
            new Circle(Point.randomPoint());
        }
    
    }
}
