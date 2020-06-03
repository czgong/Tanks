import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;

public class BulletOne implements BulletInterface{
    public int[] position;
    public boolean readyToFire;
    public boolean isIntersecting;
    public boolean isFiring;
    BufferedImage imageBullet = null;

    //default constructor
    public BulletOne(){

    }

    //constructor for bullet one
    public BulletOne(int x, int y) {
        position = new int[]{x + 30, y - 15};
        readyToFire = false;
        isIntersecting = false;
        isFiring = false;
    }

    //draw bullet one
    public void draw(Graphics g) {
        /*https://javaconceptoftheday.com/read-and-write-images-in-java/
         *how to import images
         *https://www.cleanpng.com/png-bullet-icon-a-bullet-95092/download-png.html
         * bullet image
         */
        if (isFiring) {
            try {

                File BulletOne = new File("Bullet.png");
                imageBullet = ImageIO.read(BulletOne);

            } catch (IOException e) {
                e.printStackTrace();
            }

            g.drawImage(imageBullet, position[0], position[1], width, height, null);
        }
    }

    //move bullet one for left to right movement
    public void moveBullet(){
        if(isFiring) {
            position[0] = position[0] + velocity;
        }
    }


    //check if the bullet hits the left side of the given wall dimensions and returns t/f
    public boolean checkIfHittingWall ( int x, int y, int width, int height){
            if ((position[0] + this.width >= x)
                    && ((position[1] + this.height) > y)
                    && (position[1] < (y + height))
                    && (position[0] < x)) {
                stop();
                return true;
            }
            return false;
        }

        //makes the bullet fire
        public void fire () {
            isFiring = true;
        }

        //stops the bullet
        public void stop () {
            isFiring = false;
        }
}
