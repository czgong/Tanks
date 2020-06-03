import java.awt.Graphics;

//parent class to square wall and circle wall
public class  Wall {
    public int[] position;
    public int width;
    public int height;

    //never used, but needed a default constructor
    public Wall (){}

    //will always be overridden by child methods
    public void draw(Graphics g){}


    //check if the player is running into any of the four sides of the wall
    public void isInBoundaries(PlayerOne p){
        //From the left of the wall, checking the x first then y constraints
        if(((p.position[0] + p.radius) > this.position[0]) &&((p.position[1] + p.radius) > this.position[1])
                && (p.position[1] < (this.position[1] +this.height))&&(p.position[0]<this.position[0])
                && ((Math.abs(p.position[1] - this.position[1] + this.height/2)) < (this.height - 5))){
            p.flipXVel();
            p.position[0] = p.position[0] - 1;
        }
        //From right going to the left
        else if((p.position[0] < (this.position[0]+this.width)) &&((p.position[1] + p.radius) > this.position[1])
                && (p.position[1] < (this.position[1] +this.height))&&(p.position[0]>this.position[0])
                && ((Math.abs(p.position[1] - this.position[1] + this.height/2)) < (this.height - 5))){
            p.flipXVel();
            p.position[0] = p.position[0] + 1;
        }
        //From the top going down
        if(((p.position[1] + p.radius) > this.position[1]) &&((p.position[0] + p.radius) > this.position[0])
                && (p.position[0] < (this.position[0] +this.width))&&(p.position[1]<this.position[1])
                ){
            p.flipYVel();
            p.position[1] = p.position[1] - 1;
        }
        //From the bottom going up
        if((p.position[1] < (this.position[1]+this.height)) &&((p.position[0] + p.radius) > this.position[0])
                && (p.position[0] < (this.position[0] +this.width))&&(p.position[1]>this.position[1])){
            p.flipYVel();
            p.position[1] = p.position[1] + 1;
        }
    }

}