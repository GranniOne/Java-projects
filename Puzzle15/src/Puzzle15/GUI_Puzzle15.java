package Puzzle15;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI_Puzzle15 extends JPanel implements MouseListener{

    public static int ScoreCounter = 0;
    public static PuzzleBoard pb = new PuzzleBoard();

    //klassen der extender JPanel og implementere MouseListener, og er derfor det grafiske interface.
    GUI_Puzzle15() {
        addMouseListener(this);
        setVisible(true);
        repaint();
    }

    //paint metode til at a lave det grafiske interface på JPanel GUI_Puzzle15
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //først bliver der oprettet et objekt af PuzzleBoard klassen, som indeholder et 2D array med tal fra 1-15
        int[][] ranNums = pb.puzzleBoard;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0 , 482, 482); //baggrunds farven på spillet.

        //her bliver der lavet en for løkke der går igennem 2D arrayet og tegner brikkerne af puzzle15.
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                //der bliver kigget efter om værdien af ranNums[j][i] er -1, hvis den er det bliver der tegnet en hvid brik.
                //den laver så en række firkanter med brug af værdierne j og i som henholdsvis er x og y koordinaterne.
                if (ranNums[j][i] != -1) {
                    g.setColor(Color.BLUE);
                    g.fillRect((120 * j)+2, (120 * i)+2, 118, 118);
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(ranNums[j][i]), 120 * j + 60, 120 * i + 60);
                }
                else {
                    g.setColor(Color.WHITE);
                    g.fillRect((120 * j)+2, (120 * i)+2, 118, 118);
                    g.setColor(Color.BLACK);
                }
            }
        }
        //her bliver der tegnet en grå boks med teksten "score" og en hvid boks med scoren.
        g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        g.drawRect(135, 503, 200, 50);
        g.drawString("du har brugt "+ (ScoreCounter) +" forsøg", 140, 540);
        g.fillRect(135, 575, 200, 50);
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("reset", 187, 615);

    }
    //her bliver der brugt mouselistener til at finde ud af hvor musen er når der bliver klikket
    @Override
    public void mouseClicked(MouseEvent e) {
       //der bliver først fundet x og y koordinaterne for musen.
       int current_x = e.getX();
       int current_y = e.getY();
        //her bliver der fundet det specifikke felt som musen er i ved at dividere med 120 altså bredden af brikken
        //og så har vi koordinatet til den brik som musen er i som kan bruges til arrayen.
       int placement_x = current_x/120;
       int placement_y = current_y/120;
       //det gemmes i en variable som tager en liste af x og y koordinaterne.
       int[] cords = {placement_x,placement_y};
       //her bliver det tjekket om koordinaterne er inden for brættet og hvis de er bliver der kaldt en metode fra PuzzleBoard klassen.
       if (placement_x < 4 && placement_y < 4) {
           pb.MovePiece(cords);
           roundLoss();
       }
       else {
           //og her bliver musens koordinatr sendt til gamerestart og tjekker så om det vil være indenfor den specifikke felt.
           gameRestart(current_x, current_y);
       }
        //her bliver der kaldt repaint metoden som tegner brikkerne igen.
        repaint();
    }

    //metoden som tjekker om spilleren har tabt.
    public void roundLoss(){
        //hvis scoren af spilleren er 400 vil der åbnes et optionpane som fortæller at du har tabt.
        //den vil så lukke jframen og køre main metoden igen.
        if (ScoreCounter == 400) {
            pb = new PuzzleBoard();
            JOptionPane.showMessageDialog(
                    null,
                    "You lost, try again",
                    "You lost",
                    JOptionPane.INFORMATION_MESSAGE);
            repaint();
            ScoreCounter = 0;
        }
    }
    public void gameRestart(int cordx, int cordy){
        if ((cordx >=135 && cordx <= 335) && (cordy >=575 && cordy <= 625)){
            pb = new PuzzleBoard();
            repaint();
            ScoreCounter = 0;
        }
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
}

