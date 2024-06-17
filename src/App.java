import javax.swing.*;
import java.lang.*;
//Swing is a Java Foundation Classes [JFC] library : used to build GUIs
//and an extension of the 
//Abstract Window Toolkit [AWT]:used to develop GUI (Graphical User Interface) or window-based applications in Java. Basically, the AWT is a member of the Java Foundation Classes – the approved API implementing a GUI for a Java program.
// Java Swing offers much-improved functionality over AWT, new components, expanded components features, and excellent event handling with drag-and-drop support.


public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth= 360;
        int boardHeight= 640;

        // Thе Java JFramе is an еssеntial componеnt of Java Swing, which is a part of thе Java SWT(Standard Widgеt Toolkit). JFrame in Java is a class that allows you to crеatе and manage a top-lеvеl window in a Java application.
        // platform-indеpеndеnt way to crеatе graphical usеr intеrfacеs.

        JFrame frame=new JFrame("Flappy Bird");
        //frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight); //op window size
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create an instance of jpanel i.e flappybird and add it to frame
        FlappyBird flappybird= new FlappyBird();

        frame.add(flappybird);
        frame.pack();// used to exclude title bar from the jpanel size, else it would include title bar size in total size of panel 
        
        flappybird.requestFocus();//imp line of code after setting focusable() in flappy constructor
        frame.setVisible(true);//set visible is true after adding panwl n other imp components


       

       
        



    }
}
