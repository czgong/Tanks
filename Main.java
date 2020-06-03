import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends JPanel implements KeyListener{
    public final static int FPS = 60;
    private World myWorld;
    private boolean endGame = false;
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    private static int secondsPassed2=3;
    private static int secondsPassed1 = 3;
    //https://www.geeksforgeeks.org/java-util-timer-class-java/
    //how to use timers
    private static Timer timer = new Timer();
   //making the timer increment by one second
    private static TimerTask task = new TimerTask(){

        public void run(){
            secondsPassed1++;
            secondsPassed2++;
        }
    };


    //starts the timer
    public static void start() {
        timer.scheduleAtFixedRate(task, 1000, 1000);

    }

    //copied mostly from bouncing spheres lab
    public static void main(String args[]) {
        JFrame frame=new JFrame("Request of War");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainInstance = new Main();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
        start();

    }

    /*creating a World object and keylistener
    * used bouncing spheres as guideline here
    */
    public Main(){
        myWorld = new World();
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Thread mainThread = new Thread(new Painter());
        mainThread.start();
    }

    //taken from bouncing spheres lab and understood how it works
    // computes and updates all our objects "behind the scenes." does the math aspect
    class Painter implements Runnable {
        public void run() {
            while (true) {
                myWorld.updatePlayers();
                myWorld.checkPersonInWall();
                myWorld.checkBulletHitsWall();

                //once bullet is created, it will move and check if hit player
                if(myWorld.myBullet1 != null) {
                    myWorld.myBullet1.moveBullet();
                    myWorld.checkBulletHitsP2();;
                }
                if(myWorld.myBullet2 != null) {
                    myWorld.myBullet2.moveBullet();
                    myWorld.checkBulletHitsP1();
                }

                //check if the game has ended
                if(myWorld.checkEndGame())
                    endGame = true;

                repaint();

                try {
                    Thread.sleep(1000 / FPS);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    //movement and shooting keys
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();

        //player one directional keys
        if(c=='s')
            myWorld.player1.yVel = myWorld.player1.SPEED;
        if(c=='w')
            myWorld.player1.yVel = -myWorld.player1.SPEED;
        if(c=='a') {
            myWorld.player1.xVel = -myWorld.player1.SPEED;
        }
        if(c=='d')
            myWorld.player1.xVel = myWorld.player1.SPEED;
        //refresh rate for shooting is 2 seconds
        if(secondsPassed1 > 2) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                myWorld.myBullet1 = new BulletOne(myWorld.player1.position[0],myWorld.player1.position[1]);
                myWorld.myBullet1.fire();
                secondsPassed1 = 0;
            }
        }

        //player two directional keys
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            myWorld.player2.xVel = -myWorld.player2.SPEED;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            myWorld.player2.xVel = myWorld.player2.SPEED;
        if(e.getKeyCode() == KeyEvent.VK_UP)
            myWorld.player2.yVel = -myWorld.player2.SPEED;
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            myWorld.player2.yVel = myWorld.player2.SPEED;
        if(secondsPassed2>2){
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                myWorld.myBullet2 = new BulletTwo(myWorld.player2.position[0],myWorld.player2.position[1]);
                myWorld.myBullet2.fire();
                secondsPassed2=0;

            }
        }


    }

    //resetting the directions so that once let go, player stops moving
    public void keyReleased(KeyEvent e) {
        char c=e.getKeyChar();

        //player one directional keys
        if(c == 'w' || c == 's')
            myWorld.player1.yVel = 0;
        if(c == 'a' || c == 'd')
            myWorld.player1.xVel = 0;

        //player two directional keys
        if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP)
            myWorld.player2.yVel = 0;
        if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
            myWorld.player2.xVel = 0;

        //resetting the game by pressing enter
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(endGame) {
                endGame = false;
                myWorld.reset();
            }
        }


    }

    //not being used, but will cause error with other key methods if deleted
    public void keyTyped(KeyEvent e) {

    }

    //taken from bouncing spheres lab
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    //paints the game
    public void paintComponent(Graphics g) {
        //if the game is not over
        if(!endGame) {
            Graphics2D a = (Graphics2D) g.create();
            myWorld.drawTerrain(g);
            myWorld.drawPlayers(g);
            myWorld.drawBullet(g);

            //prints the 'ready to fire!' if player's bullet refresh has cooled down
            if(secondsPassed1 > 2) {
                g.setColor(Color.YELLOW);
                g.drawString("Ready to Fire!", 25, 40);
            }
            if(secondsPassed2 > 2) {
                g.setColor(Color.YELLOW);
                g.drawString("Ready to Fire!", 915, 40);
            }

        }
        //if the game is over
        else {
            g.setColor(Color.BLACK);
            g.fillRect(0,0, WIDTH, HEIGHT);

            g.setColor(Color.WHITE);
            g.drawString("Game Over!", WIDTH/2- 100,HEIGHT/2 - 100);
            g.drawString("Press \"Enter\" to Play Again!", WIDTH/2 - 85 ,HEIGHT/2 + 50);
            if(myWorld.player1.health < 0)
                g.drawString("Player 2 Wins!",  WIDTH/2 ,HEIGHT/2 -100 );
            else
                g.drawString("Player 1 Wins!", WIDTH/2 ,HEIGHT/2 - 100);
        }
    }



}