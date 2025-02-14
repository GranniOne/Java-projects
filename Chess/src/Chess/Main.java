package Chess;

import javax.swing.*;


public class Main {

    public static JFrame frame = new JFrame("Chess");

    public static void main(String[] args) {
        ChessBoard_GUI chessBoard_gui = new ChessBoard_GUI();
        frame.add(chessBoard_gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}