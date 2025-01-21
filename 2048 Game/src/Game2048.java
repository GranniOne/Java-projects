import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.Arrays;


public class Game2048 extends JPanel implements MouseListener, KeyListener {


    int current_X;
    int current_Y;
    Font font = new Font("TimesRoman", Font.PLAIN, 50);
    Timer ActionTimer;
    GameData gameData;
    Color[] colorScheme = new Color[]{new Color(170, 215, 81),new Color(162, 209, 73)};


    int BrickSize = 160;
    int InnerbrickSize = 140;


    Game2048(){
        setSize(1200,1200);
        setFocusable(true);
        requestFocusInWindow();
        addMouseListener(this);
        addKeyListener(this);
        Timer();
        setVisible(true);
        gameData = new GameData();









    }


    private void Timer() {
        ActionListener TimerActionListener = e ->
        {
            for(int i = 0; i < 4; i++){
                for(int j = 0 ; j < 4;j++){
                    if(gameData.BoardData[i][j] == 2048){
                        System.out.println("You Win");
                    }
                }
            }
            repaint();
        };


        ActionTimer = new Timer(20,TimerActionListener);
        ActionTimer.start();




    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < 4; i++){

            for(int j = 0 ; j < 4;j++){

                    g.setColor((i + j) % 2 == 0 ? colorScheme[0] : colorScheme[1]);
                    g.fillRect((BrickSize * i) + 317, (BrickSize * j) + 20, BrickSize, BrickSize);
                    g.setColor(Color.black);
                    g.drawRect((BrickSize * i) + 317, (BrickSize * j) + 20, BrickSize, BrickSize);
                    g.setFont(font);


                if(gameData.BoardData[i][j] != 0){
                    if (gameData.BoardData.length * i + j == gameData.getLatestElement()) {
                        g.setColor(Color.red);
                    } else {
                        g.setColor(Color.black);
                    }
                    g.fillRect((BrickSize*i)+317+10,(BrickSize*j)+20+10,InnerbrickSize,InnerbrickSize);
                    g.setColor(Color.white);



                    int textWidth = g.getFontMetrics(font).stringWidth(String.valueOf(gameData.BoardData[i][j]));
                    int textHeight = g.getFontMetrics(font).getHeight();
                    g.drawString(String.valueOf(gameData.BoardData[i][j]),(BrickSize*i)+317+80-(textWidth/2),(BrickSize*j)+20+70+(textHeight/2));



                }

            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        current_X = e.getX();
        current_Y = e.getY();

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        switch (key) {
            case 'w': // Up
                gameData.BoardData = gameData.move(gameData.BoardData,"up");
                break;
            case 's': // Down
                gameData.BoardData = gameData.move(gameData.BoardData,"down");
                break;
            case 'a': // Left
                gameData.BoardData = gameData.move(gameData.BoardData,"left");
                break;
            case 'd': // Right
                gameData.BoardData = gameData.move(gameData.BoardData,"right");
                break;
        }
        if(String.valueOf(key).matches("[wasd]")){
            gameData.LosingCondition();

        }



    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
