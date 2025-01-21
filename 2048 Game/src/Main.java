import javax.swing.*;
import java.awt.event.KeyListener;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("2048 Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1200,1200);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(new Game2048());
        frame.setVisible(true);
        System.out.println(frame.getWidth());





    }
}