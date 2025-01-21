import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class ChessBoard_GUI extends JPanel implements MouseListener {



    public static ChessBoard chessBoard = new ChessBoard();

    public static int[] chosenPieceCords = {-1,-1};

    public static int[] chosenLokationCords = {-1,-1};
    boolean whiteTurn = true;


    ChessBoard_GUI(){
        addMouseListener(this);
        setVisible(true);
    }

    static boolean someoneWon = false;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.fillRoundRect(280,30,680,680,10,10);
        g.setColor(new Color(130,92,81,255));
        g.fillRoundRect(40,210,240,320,10,10);
        g.setColor(new Color(250, 216, 168,255));
        g.fillRoundRect(960,210,240,320,10,10);
        g.fillRoundRect(100,50,120,120,10,10);

        if (whiteTurn) {
            g.setColor(new Color(255, 255, 255, 255));
        } else {
            g.setColor(new Color(0, 0, 0, 255));
        }
        g.fillRect(1000,50,120,120);

        int colorIndex = 0;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                //checks if the current location in the chessboard array is even and if so paints it brown
                if (colorIndex%2 == 0 ) {
                    g.setColor(new Color(130,92,81,255));
                    g.fillRect((80 * j)+300, (80 * i)+50, 80, 80);
                }
                //else its gonna paint it white brown
                else  {
                    g.setColor(new Color(250, 216, 168,255));
                    g.fillRect((80 * j)+300, (80 * i)+50, 80, 80);
                }
                //checks if the current chosen coordinates for the piece is -1-1 which is also when none is selected
                if (!Arrays.equals(chosenPieceCords, new int[]{-1, -1})){
                    //finds the string of the current chess piece, and check all the allowed moves for the piece
                    //compared to the location of the chosen piece
                    boolean rules = ChessRules.Chessrules(chessBoard.ChessPieceType(chosenPieceCords),chosenPieceCords,new int[]{i,j});
                    //Finds the current selected piece if the placement equals to the mouseclick coordinates
                    //and if the piece is empty.
                    if(!Objects.equals(chessBoard.chessBoard[i][j], "")){
                        g.setColor(new Color(0, 84, 255, 25));
                        g.fillRect((80 * chosenPieceCords[1])+300, (80 * chosenPieceCords[0])+50, 80, 80);
                    }
                    //finds all the allowed move for the piece and paints them
                    if (rules){
                        g.setColor(new Color(0, 255, 0, 75));
                        g.fillRect((80 * j)+300, (80 * i)+50, 80, 80);
                    }
                }
                if (!Objects.equals(chessBoard.chessBoard[i][j], "")){
                    //klistre billeder på lorten
                    try {
                        Image image = ImageIO.read(new File(String.format("C:\\Users\\mathi\\IdeaProjects\\Skak\\SkakPics\\%s",chessBoard.chessBoard[i][j]+".png")));
                        g.drawImage(image, 80*j+10+300, 80*i+10+50,60,60, this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                colorIndex++;
            }
            colorIndex++;
        }
    }

    //a method that releases all used variables and creates a new chessboard instance
    void resetGame(){
        chessBoard = new ChessBoard();
        chosenPieceCords = new int[]{-1, -1};
        chosenLokationCords = new int[]{-1, -1};
        repaint();
    }

    void moveLogic(int[] cords){
        if (Arrays.equals(chosenPieceCords, new int[]{-1, -1})){
            chosenPieceCords = cords;
            //repaints the directions that a board piece can move to
            repaint();
        }else{
            chosenLokationCords = cords;
        }

        if (!Arrays.equals(chosenPieceCords, new int[]{-1, -1})&& !Arrays.equals(chosenLokationCords, new int[]{-1, -1})){
            if(!Arrays.equals(chosenLokationCords, chosenPieceCords)){
                if (ChessRules.Chessrules(chessBoard.ChessPieceType(chosenPieceCords),chosenPieceCords,chosenLokationCords)){
                    if((chessBoard.ChessPieceType(chosenPieceCords).startsWith("white") && whiteTurn) ||
                        (chessBoard.ChessPieceType(chosenPieceCords).startsWith("black") && !whiteTurn)){
                        System.out.println("samme plads");
                        whiteTurn = !whiteTurn;

                        chessBoard.ChessMove(chosenPieceCords,chosenLokationCords);

                        IsAlive(Arrays.stream(chessBoard.chessBoard).flatMap(Arrays::stream).anyMatch(piece -> piece.equals("white_king")),
                                Arrays.stream(chessBoard.chessBoard).flatMap(Arrays::stream).anyMatch(piece -> piece.equals("black_king")),someoneWon);
                        }
                    }
                }
            chosenPieceCords = new int[]{-1, -1};
            chosenLokationCords = new int[]{-1,-1};
            repaint();
        }
    }
    void IsAlive(boolean whiteAlive,boolean blackAlive,boolean someoneWon){
        // print om nogen har vundet
        if(!blackAlive && !someoneWon){
            JOptionPane.showMessageDialog(null, "den sorte konge er død og hvid vinder :)");
            resetGame();
        }
        if(!whiteAlive&& !someoneWon){
            JOptionPane.showMessageDialog(null, "den hvide konge er død og sort vinder :)");
            resetGame();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int current_y = (e.getX()-300)/80;
        int current_x = (e.getY()-50)/80;
        if(e.getX() > 100 && e.getX() < 220 && e.getY() > 50 && e.getY() < 170){
            resetGame();
        }
        else if (current_x >= 0 && current_x < 8 && current_y >= 0 && current_y < 8){
            moveLogic(new int[]{current_x,current_y});
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
