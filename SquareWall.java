import java.awt.*;

//Square wall class
public class SquareWall extends Wall {
    public SquareWall(int x, int y){
        position = new int[]{x, y};
        height = 60;
        width = 30;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(position[0], position[1], width, height);
    }


}