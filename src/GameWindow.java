import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import java.awt.event.MouseMotionListener;
import java.util.Random;

public class GameWindow extends JPanel implements MouseMotionListener {

    private Game game;

    int BrickCatcherX;
    int BrickCatcherY;
    int BrickCatcherWidth = 100;
    int BrickCatcherHeight = 20;

    int ballX = 1200;
    int ballY = 700;
    int ballRadius = 20;
    int DirectionX = 1;
    int DirectionY = 1;
    int DirectionXY = 1;



    public GameWindow() {
        game = new Game();
        this.setVisible(true);
        this.addMouseMotionListener(this);
        this.setBackground(Color.BLACK);
        timer.start();


    }

    Timer timer = new Timer(1000/165, e -> {
        repaint();
    });

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(BrickCatcherX,1300,BrickCatcherWidth,BrickCatcherHeight);
        g.fillRoundRect(ballX, ballY, ballRadius, ballRadius, ballRadius, ballRadius);
        g.setColor(Color.WHITE);
        for(BrickObject brick : game.BrickCollection) {
            g.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
        }


    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        BrickCatcherX = e.getX()-BrickCatcherWidth/2;
        int MouseX = e.getX();
        int MouseY = e.getY();

        for(BrickObject brick : game.BrickCollection) {
            if(brick.getX() < MouseX&&
                    brick.getX() + brick.getWidth() > MouseX &&
                    brick.getY() < MouseY &&
                    brick.getY() + brick.getHeight() > MouseY) {
                game.BrickCollection.remove(brick);
                DirectionY = -DirectionY;
            }

        }

        System.out.println("X: "+BrickCatcherX+" Y: "+BrickCatcherY);


    }
}
