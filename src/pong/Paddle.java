package pong;

import java.awt.*;

public class Paddle {

    private int x, y;
    private int vel = 0;
    private int speed = 10;
    private int WIDTH = 20, HEIGHT = 150;
    private int score = 0;
    private Color color;
    private boolean left,right;

    //constructor
    public Paddle(Color c, boolean left){
        color = c;

        this.left = left;
        if(left){
            x = 0;
        }else{
            x = Game.WIDTH - this.WIDTH;
        }
        y = Game.HEIGHT / 2 - this.HEIGHT / 2;
    }

    public void addPoint(){
        score++;
    }

    public void draw(Graphics graphics) {
        //draw paddle
        graphics.setColor(color);
        graphics.fillRect(x,y,WIDTH,HEIGHT);

        //draw score
        int sx; //position x of the String
        String scoreText = Integer.toString(score);
        Font font = new Font("Roboto", Font.PLAIN, 50);

        int strWidth = graphics.getFontMetrics(font).stringWidth(scoreText) + 1;
        int padding  = 25;
        if(left){
            sx = Game.WIDTH/2 - padding - strWidth;
        }else{
            sx = Game.WIDTH / 2 + padding;
        }
        graphics.setFont(font);
        graphics.drawString(scoreText, sx, 50);
    }

    public void update(Ball ball) {
        //update position
        y = Game.ensureRange(y += vel, 0, Game.HEIGHT - this.HEIGHT );
        int ballX = ball.getX();
        int ballY = ball.getY();

        //collision with the ball
        if(left){

            if(ballX <= this.WIDTH && ballY + ball.WIDTH >= y && ballY <= y + HEIGHT ) {
                ball.changeXDirection();
            }
        }else{
            if(ballX + ball.WIDTH >= Game.WIDTH - this.WIDTH && ballY + ball.HEIGHT >= y && ballY <= y + HEIGHT){
                ball.changeXDirection();
            }
        }
        //the bottom left corner occur bug, the ball not disappear, it collision .
    }

    public void switchDirection(int direction) {
        vel = speed * direction;
    }
    public void stop(){
        vel = 0;
    }
}
