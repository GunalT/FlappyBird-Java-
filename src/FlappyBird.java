import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
//stores pipes in game

import java.util.Random;
//places pipes in randomlocations;

import javax.swing.*;//has jpanel


//JPanel, a part of the Java Swing package,
// is a container that can store a group of components.
//it does not have a title bar
//The JPanel is a simplest  lightweight  container class.
// It provides space in which an application can attach any other component.

public class FlappyBird extends JPanel implements ActionListener,KeyListener
{


    int boardWidth= 360;
    int boardHeight=640;

    //image object variables to store images

    Image backgndImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Bird on the screen

    int birdX= boardWidth/8;
    int birdY=boardHeight/2;
    int birdWidth=34;
    int birdHeight=24;


    class Bird{

        int x= birdX;
        int y=birdY;
        int width=birdWidth;
        int height=birdHeight;
        Image img;

        Bird(Image img){
            this.img=img;

        }
    }

    //pipes

    int pipeX= boardWidth;
    int pipeY=0;

    int pipeWidth=64;
    int pipeHeight=512;


    class Pipe{

        int x=pipeX;
        int y=pipeY;
        int width=pipeWidth;
        int height=pipeHeight;
        Image img;
        boolean passed=false;

        Pipe(Image img){

            this.img= img;
        }

    }

    //game logics

    Bird bird;

    //bird moving along aqxis y only in game not sidewards
    //pipes moves towards left

    int velocityX=-4;//pipe motions to left and bird right


    int velocityY=0;//position along y axis of bird, up &down motion of bird
    int gravity=1; //slowing the bird by 1px in every frame;


    ArrayList<Pipe> pipes;
    Random random= new Random();



    Timer gameloop;//bird motion
    Timer placePipesTimer;

    boolean gameOver= false;

    double score =0;





    FlappyBird(){ //constructor


        setPreferredSize(new Dimension(boardWidth,boardHeight));
       // setBackground(Color.BLUE);

       setFocusable(true);// this makes sure this class to takes key events

       addKeyListener(this);//checks dfor keylistener overriden methods
        //load Images

        

        backgndImg= new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg=new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg= new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        
        //this is used store the bird object with image n variables ,as given in the template!
        bird = new Bird(birdImg);
        pipes= new ArrayList<Pipe>();

        //pipe timer
        placePipesTimer=new Timer(1500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                placePipes();
                
            }

            
        });

        placePipesTimer.start();

        //game timer

        gameloop= new Timer(1000/60,this);// 1000ms/60 because we wan tto draw like 60 frames per second 
        //this refer sto flappy bird class
        
        //loop used to repeat action per second
        gameloop.start();
    
    }
    //function used to make pipes

    public void placePipes(){

        // random gives values b/w(0-1) * pipeHeight/2 -> (0-256)
        //128
        //0-128-(0 to 256) ---> pipeHeight/4 -> 3/4 pipeHeight

        int randomPipeY=(int) (pipeY - pipeHeight/4- Math.random()*pipeHeight/2);
        int openingSpace= boardHeight/4;


        Pipe topPipe= new Pipe(topPipeImg);     
        topPipe.y=randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y= topPipe.y +pipeHeight+openingSpace;
        pipes.add(bottomPipe);


    }

        public void paintComponent(Graphics g){

            super.paintComponent(g);// super refers to(parent) jpanel, bcuz we inherit jpanel
            draw(g);

        }

        public void draw(Graphics g){

            //debug statement 
           // System.out.println("draw");
            
           //background draw
            g.drawImage(backgndImg, 0, 0, boardWidth,boardHeight,null);
            //(add a image, x-axis, y-axis, width of screen,height,null);
            //x- & y axis=0,0 to refer to topleft corner of the frame

                //bird draw()
            g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

            //pipes

            for(int i=0;i<pipes.size();i++){

                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipeHeight, null);
            }

            //score
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.PLAIN,32) );
            if(gameOver){
                g.drawString("Game Over: " + String.valueOf((int) score) , 10, 35);
                g.setFont(new Font("Arial",Font.PLAIN,25) );
                g.drawString("Press SpaceBar to Restart!", 30,boardHeight/2 );
            }
            else{
                g.drawString(String.valueOf((int)score), 10 , 35);
            }


        }
        //updating x &y position of objects using move()

        public void move(){


            //bird

            velocityY +=gravity;
            bird.y += velocityY;//increase bird y-coordinate by 1 to move downward

            bird.y= Math.max(bird.y,0);//to stop movement iof bird beyond the screen 
            
            //pipes
            for(int i=0;i<pipes.size();i++){

                Pipe pipe =pipes.get(i);
                pipe.x+= velocityX;

                if(!pipe.passed && bird.x >pipe.x +pipe.width ){//bord passrs right side of pipe
                    pipe.passed=true;
                    score +=0.5;

                }

                if(collision(bird, pipe)){
                    gameOver=true;
                }

            }


            if(bird.y>boardHeight){
                gameOver=true;
            }
        }

        public boolean collision(Bird a, Pipe b){

            return a.x<b.x+b.width && // bird top left corner doesnt reah pipes topn right corner
                    a.x+a.width>b.x &&// bird top right corner passes pipes top left corner
                    a.y<b.y+b.height &&// bird top left corner doesnt reach pipes bottom left cornr
                    a.y +a.height>b.y; // bird bottom left corner passes pipes top left corner


        }

        //action listener methos overriden
        @Override
        public void actionPerformed(ActionEvent e) {
      
            //action performed  60 times/second bith move & repaint
                            
            //move the bird upward or downward
            move();


// calling the paint component using repaint()


            repaint();

        

        if(gameOver){

            //stops making new pipes in game, stopping repaint() 

            placePipesTimer.stop();
            gameloop.stop();
        }
    }



        //keylistener overriden methods

        

        @Override
        public void keyPressed(KeyEvent e) {
            
            if(e.getKeyCode()== KeyEvent.VK_SPACE){
            //System.out.println(velocityY );
                velocityY += -9;

                if(gameOver){
                    //restart

                    bird.y=birdY;
                    velocityY=0;
                    pipes.clear();
                    score=0;
                    gameOver=false;
                    gameloop.start();
                    placePipesTimer.start();
                }

            }

        }

        @Override
        public void keyTyped(KeyEvent e) {
           //not using
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //not using
            if(e.getKeyCode()== KeyEvent.VK_SPACE){
                System.out.println(velocityY );
                    velocityY =0;
    
                }
        }

    





    
}
