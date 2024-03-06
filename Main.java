import javax.swing.*;

public class Main {

    public static JFrame frame = new JFrame("Puzzle15");

    public static void main(String[] args) {
        GUI_Puzzle15 gui = new GUI_Puzzle15();
        frame.add(gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(496, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



    }
}






