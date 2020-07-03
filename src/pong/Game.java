package pong;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -1L;
    public static final int WIDTH = 1000, HEIGHT = WIDTH * 9/16; // 16:9 ratio for game

    public boolean running  = false;
    private Thread gameThread; //create the thread

    private Ball ball;
    private Paddle paddle1;
    private Paddle paddle2;

    public MainMenu mainMenu;

    public Game(){
        canvasSetup();
        new Window("Simple Pong game", this); // create window to perform "this" game

        initialize();
        this.addKeyListener(new KeyInput(paddle1, paddle2));
        this.addMouseListener(mainMenu);
        this.addMouseMotionListener(mainMenu);
        this.setFocusable(true);

    }

    public static int sign(double v) {
        if(v <= 0){
            return -1;
        }
        return 1;
    }

    public static int ensureRange(int val, int min, int max) {
        return Math.min(Math.max(val,min), max);
    }

    private void initialize(){
        //initialize ball
        ball = new Ball();


        //initialize paddle
        paddle1 = new Paddle(Color.red, true); // left paddle
        paddle2 = new Paddle(Color.green, false); // right paddle

        //initialize menu
        mainMenu = new MainMenu(this);


    }
    private void canvasSetup() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void run() {
        //game loop
        this.requestFocus();
        //game timer

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                update();
                delta--;
            }
            if(running){
                draw();
            }
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000; // 1 second
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void draw() {
        //initialize drawing tool
        BufferStrategy buffer = this.getBufferStrategy();
        if(buffer == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics graphics = buffer.getDrawGraphics();
        // draw background
        drawBackground(graphics);
        if(mainMenu.active){
            mainMenu.draw(graphics);
        }

        //draw paddle and score
        paddle1.draw(graphics);
        paddle2.draw(graphics);

        //draw the ball
        ball.draw(graphics);

        //dispose, actually draw
        graphics.dispose();
        buffer.show();
    }

    private void drawBackground(Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.fillRect(0,0,WIDTH,HEIGHT);

        //dotline;
        graphics.setColor(Color.white);
        Graphics2D graphics2D = (Graphics2D) graphics;
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10},0);
        graphics2D.setStroke(dashed);
        graphics2D.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);

    }

    private void update() {
        if(!mainMenu.active){
            //update ball
            ball.update(paddle1, paddle2);
            //update paddle
            paddle1.update(ball);
            paddle2.update(ball);
        }


    }

    public void start() {
        gameThread = new Thread(this); //initialize the thread for game
        gameThread.start();
        running = true;
    }
    public void stop(){
        try {
            gameThread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args){
        new Game();
    }
}
