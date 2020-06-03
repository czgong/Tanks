import java.awt.*;

//player interface
public interface PlayerInterface {
    int radius = 40;

    void draw(Graphics g);

    void drawHealth(Graphics g);

    void reduceHealth();

    int[] setPosition(int x, int y);

    void updatePosition();

    void checkIfPlayerHitsBarrier();

    void flipXVel();

    void flipYVel();

    boolean isContaining(int x, int y);

    boolean bulletIsHittingPlayer(int[] p);



}
