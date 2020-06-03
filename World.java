import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import java.awt.Graphics;

public class World {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public PlayerOne player1;
    public PlayerTwo player2;
    public ArrayList<Wall> myWalls;
    private boolean firstSetUp = true;
    public BulletOne myBullet1;
    public BulletTwo myBullet2;
    private int wallPositionX;
    private int wallPositionY;
    private Random rand = new Random();

    //constructor takes in the created players from main
    public World() {
        player1 = new PlayerOne(new int[]{75,350});
        player2 = new PlayerTwo(new int[]{899, 350});
        myWalls= new ArrayList<Wall>();
    }

    //update the position of both players
    public void updatePlayers() {
        player1.updatePosition();
        player2.updatePosition();

    }

    //draw both players
    public void drawPlayers(Graphics g) {
        player1.draw(g);
        player2.draw(g);
    }
    //draw each bullet if they are being shot
    public void drawBullet(Graphics g) {
        if (myBullet1 != null) {
            myBullet1.draw(g);
        }
        if (myBullet2 != null) {
            myBullet2.draw(g);
        }
    }

    //draw background and walls
    public void drawTerrain(Graphics g){
        //backround color and middle line
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.red);
        g.fillRect(508, 0, WIDTH - 1016, HEIGHT);

        //walls are only created once at beginning of the game
        if (firstSetUp) {
            for (int k = 0; k < 10; k++) {
                drawWall();
            }
            firstSetUp=false;
        }

        //draw the walls
        for (int i = 0; i < myWalls.size(); i++) {
            myWalls.get(i).draw(g);
        }

    }
    //makes a new wall in the game
    public void drawWall(){
        wallPositionX = rand.nextInt(WIDTH - 150) + 75;
        wallPositionY = rand.nextInt(HEIGHT);
        //randomness to generate either a square wall or circle wall
        if ((int)(Math.random()*2) == 1) {
            //check if the position of the new wall is not intersecting with  either player
            while(isOverlapping(wallPositionX, wallPositionY, "Square")){
                wallPositionX = rand.nextInt(WIDTH - 150) + 75;
                wallPositionY = rand.nextInt(HEIGHT);
            }
            myWalls.add(new SquareWall(wallPositionX, wallPositionY));
        } else {
            while(isOverlapping(wallPositionX, wallPositionY, "Circle")){
                wallPositionX = rand.nextInt(WIDTH - 150) + 75;
                wallPositionY = rand.nextInt(HEIGHT);
            }
            myWalls.add(new CircleWall(wallPositionX, wallPositionY));
        }
    }



    //check if player two's bullet is hitting player one
    public void checkBulletHitsP1() {
        if(myBullet2 != null) {
            if (player1.bulletIsHittingPlayer(myBullet2.position))
                myBullet2 = null;
        }
    }

    //check if player one's bullet is hitting player two
    public void checkBulletHitsP2() {
        if(myBullet1 != null) {
            if (player2.bulletIsHittingPlayer(myBullet1.position))
                myBullet1 = null;
        }
    }

    //check if either bullet is colliding with any walls during the game
    public void checkBulletHitsWall() {
        if(myBullet1 != null) {
            for (int i = 0; i < myWalls.size(); i++) {
                if (myBullet1.checkIfHittingWall(myWalls.get(i).position[0], myWalls.get(i).position[1], myWalls.get(i).width, myWalls.get(i).height)) {
                    myWalls.remove(i);
                    drawWall();
                    myBullet1 = null;
                    break;
                }
            }
        }

        if(myBullet2 != null) {
            for (int i = 0; i < myWalls.size(); i++) {
                if (myBullet2.checkIfHittingWall(myWalls.get(i).position[0], myWalls.get(i).position[1], myWalls.get(i).width, myWalls.get(i).height)) {
                    myWalls.remove(i);
                    drawWall();
                    myBullet2 = null;
                    break;
                }
            }
        }

    }



    //check if person runs into the wall
    public void checkPersonInWall() {
        for (int i = 0; i < myWalls.size(); i++) {
            myWalls.get(i).isInBoundaries(player1);
            myWalls.get(i).isInBoundaries(player2);
        }

    }

    //returns a boolean if the game if over (either player loses all their health)
    public boolean checkEndGame() {
        if (player1.health < 0 || player2.health < 0)
            return true;
        else
            return false;
    }

    //resetting the game so the players have full health and start at same place
    public void reset() {
        player1.health = 100;
        player2.health = 100;
        player1.setPosition(75, 350);
        player2.setPosition(900, 350);

    }


    //method to check if new wall position will intersect with either player
    // 70 represents the diameter of the circle wall
    // 30 is the width of the square wall and 60 is the height
    //(x,y) is always the top left of the object
    public boolean isOverlapping(int x, int y, String s){
        //check if corners and middle of the circle would intersect with player 1
        if(s.equals("Circle")){
            if(player1.isContaining(x,y)
                    || player1.isContaining(x + 70,y)
                    || player1.isContaining(x, y +70)
                    || player1.isContaining(x + 70, y + 70)
                    || player1.isContaining(x + 70/2, y + 70/2)){
                return true;
            }
            //check if corners and middle of the circle would intersect with player 2
            if(player2.isContaining(x,y)
                    ||player2.isContaining(x + 70,y)
                    || player2.isContaining(x, y +70)
                    || player2.isContaining(x + 70, y + 70)
                    || player2.isContaining(x + 70/2, y + 70/2)){
                return true;
            }
            //check if other points of the circle would intersect with player 1
            if(player1.isContaining(x,y)
                    || player1.isContaining(x + 70/4,y)
                    || player1.isContaining(x, y +70/4)
                    || player1.isContaining(x + 70/2, (int)(y + 70*.75))
                    || player1.isContaining((int)(x + 70*.75), y + 70/2)){
                return true;
            }
            //check if other points of the circle would intersect with player 2
            if(player2.isContaining(x,y)
                    || player2.isContaining(x + 70/4,y)
                    || player2.isContaining(x, y +70/4)
                    || player2.isContaining(x + 70/2, (int)(y + 70*.75))
                    || player2.isContaining((int)(x + 70*.75), y + 70/2)){
                return true;
            }
        }

        if(s.equals("Square")){
            //check if corners  and middle of square wall would intersect with either player 1
            if(player1.isContaining(x,y)
                    || player1.isContaining(x + 30,y)
                    || player1.isContaining(x, y +60)
                    || player1.isContaining(x + 30, y + 60)
                    || player1.isContaining(x + 30/2, y + 60/2)){
                return true;
            }
            //check if corners and middle of the circle would intersect with player 2
            if(player2.isContaining(x,y)
                    ||player2.isContaining(x + 30,y)
                    || player2.isContaining(x, y +60)
                    || player2.isContaining(x + 30, y + 60)
                    || player2.isContaining(x + 30/2, y + 60/2)){
                return true;
            }
            //check if other points of the circle would intersect with player 1
            if(player1.isContaining(x,y)
                    || player1.isContaining(x + 30/4,y)
                    || player1.isContaining(x, y +60/4)
                    || player1.isContaining(x + 30/2, (int)(y + 60*.75))
                    || player1.isContaining((int)(x + 30*.75), y + 60/2)){
                return true;
            }
            //check if other points of the circle would intersect with player 2
            if(player2.isContaining(x,y)
                    || player2.isContaining(x + 30/4,y)
                    || player2.isContaining(x, y +60/4)
                    || player2.isContaining(x + 30/2, (int)(y + 60*.75))
                    || player2.isContaining((int)(x + 30*.75), y + 60/2)){
                return true;
            }

        }
        return false;
    }



}