import java.awt.*;

//bullet interface
public interface BulletInterface {
    int damage = 20;
    int velocity = 10;
    int width = 15;
    int height = 30;

    void draw(Graphics g);

    void moveBullet();

    boolean checkIfHittingWall(int x, int y, int width, int height);

    void fire();

    void stop();

}

