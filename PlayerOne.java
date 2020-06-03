import java.awt.Color;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerOne implements PlayerInterface{
    public int health = 100;
    public int[] position = new int[2];
    public int xVel = 0, yVel = 0;
    public int SPEED = 2;
    public BufferedImage tankImage;

    //default constructor
    public PlayerOne(){

    }

    //Player One constructor
    public PlayerOne(int[] p){
        health=100;
        position=p;
        xVel = 0;
        yVel = 0;
        SPEED = 2;
    }

    //draw player one
    public void draw(Graphics g) {
        /*https://javaconceptoftheday.com/read-and-write-images-in-java/
        *how to import images
        *https://favpng.com/png_view/tank-tanks-2d-multiplayer-png/MXCynigN
        * tank image
        */

        try {
            File playerOne = new File("Tank.png");
            tankImage = ImageIO.read(playerOne);

        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(tankImage, (int)position[0] - 20, (int) position[1] - 20, 80, 80, null);

        drawHealth(g);
    }

    //draw health bar and label it
    public void drawHealth(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(5, 0, 15, health);
        g.setColor(Color.RED);
        g.drawString("Player One Health", 25, 20);
    }

    //reduce player health
    public void reduceHealth(){
        health -= (BulletInterface.damage + (Math.random() * 15));
    }

    //set player position
    public int[] setPosition(int x, int y){
        position[0]=x;
        position[1]=y;
        return position;
    }

    //update player position
    public void updatePosition(){
        checkIfPlayerHitsBarrier();
        position[0] = position[0] + xVel;
        position[1] = position[1] + yVel;
    }

    //check if player runs into the borders of the game
    public void checkIfPlayerHitsBarrier(){
        //Check if player runs into left wall
        if(position[0] < 20){
            flipXVel();
            position[0] = 21;
        }

        //Check if player runs into right wall
        if(position[0] > World.WIDTH - 2*radius+20){
            flipXVel();
            position[0] = World.WIDTH - 2*radius+20- 1;
        }
        //middle wall barrier
        if(Math.abs(position[0] -World.WIDTH/2) < radius/2|| Math.abs(position[0] + radius -World.WIDTH/2) < 20)
            flipXVel();

        //Check if player runs into the top wall
        if(position[1] < 0){
            flipYVel();
            position[1] = 1;
        }
        //Check if player runs into the bottom wall
        if(position[1] > World.HEIGHT - 2*radius+40){
            flipYVel();
            position[1] = World.HEIGHT - 2*radius+40 - 1;
        }
    }

    //flip the x velocity
    public void flipXVel(){xVel = -xVel; }

    //flip the y velocity
    public void flipYVel(){yVel = -yVel; }

    //check if the given coordinates fall within the player's borders
    public boolean isContaining(int x, int y){
        if((x >= position[0]) && (x <= position[0] + radius)
                && (y >= position[1]) && (y <= position[1] + radius))
            return true;
        else
            return false;
    }

    //check if given coordinates are hitting the player and return if it is or not
    public boolean bulletIsHittingPlayer(int[] p) {
        if (Math.abs(p[0] - this.position[0]) < 40 && Math.abs(p[1] - this.position[1]) < 25) {
            reduceHealth();
            return true;
        }
        else
            return false;
        }
    }





