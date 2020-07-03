package pong;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends MouseAdapter {

    public boolean active;
    //button play
    private Rectangle playButton;
    private String playText = "Play";
    private boolean pHighLight = false;

    //button exit;
    private Rectangle exitButton;
    private String exitText = "Exit";
    private boolean eHighLight = false;

    private int width, height, x, y;

    private Font font;

    public MainMenu(Game game){
        active = true;
        game.start();

        width = 300;
        height = width / 2;

        y = Game.HEIGHT / 2 - height / 2;

        x = Game.WIDTH / 4 - width / 2;
        playButton = new Rectangle(x,y,width,height);

        x = Game.WIDTH * 3/4 - width /2;
        exitButton = new Rectangle(x,y,width,height);

        font  = new Font("Roboto",Font.PLAIN, 100);

    }
    public void draw(Graphics graphics){
        //cast graphics to Graphics2d
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics.setFont(font);

        graphics.setColor(Color.black);
        if(pHighLight){
            graphics.setColor(Color.white);
        }
        graphics2D.fill(playButton);

        graphics.setColor(Color.black);
        if(eHighLight){
            graphics.setColor(Color.white);
        }
        graphics2D.fill(exitButton);

        graphics.setColor(Color.white);
        graphics2D.draw(playButton);
        graphics2D.draw(exitButton);

        int strWidth;
        int strHeight;

        strWidth = graphics.getFontMetrics(font).stringWidth(playText);
        strHeight = graphics.getFontMetrics(font).getHeight();

        graphics.setColor(Color.green);
        graphics.drawString(playText, (int) (playButton.getX() + playButton.getWidth() /2 - strWidth / 2) ,
                (int) (playButton.getY() + playButton.getHeight() /2 + strHeight / 4));

        graphics.setColor(Color.red);
        graphics.drawString(exitText, (int) (exitButton.getX() + exitButton.getWidth() /2 - strWidth / 2) ,
                (int) (exitButton.getY() + exitButton.getHeight() /2 + strHeight / 4));

    }
    public void mouseClicked(MouseEvent event){
        Point point = event.getPoint();
        if(playButton.contains(point)){
            active = false;
        }else if(exitButton.contains(point)){
            System.exit(0);
        }
    }

    public void mouseMoved(MouseEvent event){
        Point point = event.getPoint();

        pHighLight = playButton.contains(point);
        eHighLight = exitButton.contains(point);



    }

}
