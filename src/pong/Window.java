package pong;

import javax.swing.*;

public class Window {

    public Window(String title, Game game){
        JFrame frame = new JFrame(title);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(game);
        frame.pack(); //pack component to the game so it make everything fit
        frame.setLocationRelativeTo(null); // make thing center of the screen
        frame.setVisible(true);

        game.start();

    }
}
