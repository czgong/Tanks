import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;

public class BulletTwo extends BulletOne implements BulletInterface{

    //constructor for bullet two
    public BulletTwo(int x, int y) {
        //minor adjustments to make the bullet line up with the barrel of tank
        position = new int[]{x - 10, y - 10};
        readyToFire = false;
        isIntersecting = false;
    }

    //draw bullet two
    public void draw(Graphics g) {
        /*https://javaconceptoftheday.com/read-and-write-images-in-java/
         *how to import images
         *https://www.cleanpng.com/png-bullet-icon-a-bullet-95092/download-png.html
         * bullet image
         */
        if (isFiring) {
            BufferedImage imageBulletTwo = null;
            try {

                File BulletTwo = new File("Bullet2.png");
                imageBulletTwo = ImageIO.read(BulletTwo);


            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(imageBulletTwo, (int) position[0], (int) position[1], width, height, null);
        }
    }

    //move bullet two from right to left movement
    public void moveBullet(){
        position[0] = position[0] - velocity;
    }

    //check to see if bullet has hit the right side of given wall dimensions and returns t/f
    public boolean checkIfHittingWall ( int x, int y, int width, int height){
        if ((position[0] <= x + width)
                && ((position[1] + this.height) > y)
                && (position[1] < (y + height))
                && (position[0] > x)) {
            stop();
            return true;
        }
        return false;
    }


}
