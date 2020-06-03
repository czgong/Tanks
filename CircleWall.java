import java.awt.*;

//Circle wall class
public class CircleWall extends Wall {

    public CircleWall(int x, int y){
        position = new int[]{x, y};
        height = 70;
        width = 70;
    }
    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fillOval(position[0], position[1], height, height);

    }



}