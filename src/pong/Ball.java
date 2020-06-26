package pong;

import java.awt.*;
import java.util.Random;

public class Ball {

    public final int WIDTH = 30;
    public final int HEIGHT = 30;

    private int x, y;
    private int xVel, yVel; //value either 1 or -1;
    private int speed = 5;


    public Ball(){
        reset();
    }

    private void reset() { // bring the ball back to start position
        //initialize position
        x = Game.WIDTH /2 - this.WIDTH / 2;
        y = Game.HEIGHT / 2 - this.HEIGHT / 2;

        //initialize velocities
        xVel = Game.sign(Math.random() * 2.0 - 1);
        yVel = Game.sign(Math.random() * 2.0 - 1);
    }
    public void changeYDirection(){
        yVel *= -1;
    }
    public void changeXDirection(){
        xVel *= -1;
    }

    public void draw(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        graphics.fillOval(x,y,this.WIDTH,this.HEIGHT);
    }

    public void update(Paddle paddle1, Paddle paddle2) {
        //update movement
        x += (xVel * speed);
        y += (yVel * speed);

        //collisions check;
        if(y + this.HEIGHT >= Game.HEIGHT || y <= 0){
            changeYDirection();
        }

        //with walls
        if(x + this.WIDTH >= Game.WIDTH){
            paddle1.addPoint();
            reset();
        }

        if(x <= 0){
            paddle2.addPoint();
            reset();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
