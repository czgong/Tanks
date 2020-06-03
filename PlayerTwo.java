import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlayerTwo extends PlayerOne implements PlayerInterface{
    public PlayerTwo(int[] p){
        health=100;
        position=p;
        xVel = 0;
        yVel = 0;
        SPEED = 2;
    }

    //draw player two
    public void draw(Graphics g){
        /*https://javaconceptoftheday.com/read-and-write-images-in-java/
         *how to import images
         *https://favpng.com/png_view/tank-tanks-2d-multiplayer-png/MXCynigN
         * tank image
         */
        try {
            File playerTwo = new File("Tank2.png");
             tankImage= ImageIO.read(playerTwo);


        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(tankImage, (int) position[0] - 20, (int)position[1] - 20, 80, 80, null);

        drawHealth(g);
    }

    //draw player two health and label
    public void drawHealth(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(1004, 0, 15, health);
        g.setColor(Color.RED);
        g.drawString("Player Two Health", 890, 20);
    }
}
