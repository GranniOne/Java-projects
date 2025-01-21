import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class Mine_Sweeper_GUI extends JPanel implements MouseListener, ActionListener {


    Board Minesweeper;
    int x_cord = 400;

    int y_cord = 100;

    int Tile_Size = 40;

    int Max_Bombs = 0;


    // GUI klasse konstruktor:
    public Mine_Sweeper_GUI(){

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Sværhedsgrad");
        JMenuItem menuItem2 = new JMenuItem("nem");
        JMenuItem menuItem3 = new JMenuItem("medium");
        JMenuItem menuItem4 = new JMenuItem("svær");


        GraphicsEnvironment GE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            File font = new File("\"C:\\Users\\mathi\\Downloads\\7segment.ttf\"");
            if (font.exists()) {
                Font FONT = Font.createFont(Font.TRUETYPE_FONT, font);
                GE.registerFont(FONT);
            }
        } catch (FontFormatException | IOException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }







        // Tilføj menu-items til menuen

        menu.add(menuItem2);
        menu.add(menuItem3);
        menu.add(menuItem4);

        // Tilføj menuen til menu-baren
        menuBar.add(menu);



        Main.frame.add(menuBar, BorderLayout.NORTH);

        menuItem2.addActionListener(this);
        menuItem3.addActionListener(this);
        menuItem4.addActionListener(this);
        addMouseListener(this);
        setVisible(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Flag: "+Max_Bombs, 500, 50);

        //oprettet nested loop til tegning af felter
        for(int i = 0; i < 14; i++){
            for(int j = 0; j < 14; j++){
                g.drawRect(Tile_Size*i+x_cord, Tile_Size*j+y_cord, Tile_Size, Tile_Size);
                Image image;

                // tegn tal og flag:
                if (Minesweeper != null){
                    if (Minesweeper.Board[i][j][1] == "p"){
                        g.drawString(Minesweeper.Board[i][j][0], Tile_Size*i+15+x_cord, Tile_Size*j+20+y_cord);
                    }
                    if (Minesweeper.Board[i][j][1] == "f"){
                        g.drawString(Minesweeper.Board[i][j][1], Tile_Size*i+15+x_cord, Tile_Size*j+20+y_cord);
                        try {
                            image = ImageIO.read(new File("C:\\Users\\mathi\\IdeaProjects\\MineSweeper\\image2.png"));
                            g.drawImage(image, Tile_Size*i+x_cord, Tile_Size*j+y_cord, Tile_Size, Tile_Size, null);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    if(Minesweeper.Board[i][j][1] == "p" && Minesweeper.Board[i][j][0] == "B"){
                        try {
                            image = ImageIO.read(new File("C:\\Users\\mathi\\IdeaProjects\\MineSweeper\\image.png"));
                            g.drawImage(image, Tile_Size*i+x_cord, Tile_Size*j+y_cord, Tile_Size, Tile_Size, null);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(Minesweeper.Board[i][j][0]);
                    }
                }
            }
        }
    }
    private void initializeBoard(int[] cords){
        Minesweeper = new Board(cords, Max_Bombs);
        Minesweeper.press(cords);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Nyt spil")){

        }
        if(e.getActionCommand().equals("nem")){
            Max_Bombs = 25;
            Minesweeper = null;
            repaint();
        }
        if(e.getActionCommand().equals("medium")){
            Max_Bombs = 35;
            Minesweeper = null;
            repaint();
        }
        if(e.getActionCommand().equals("svær")){
            Max_Bombs = 45;
            Minesweeper = null;
            repaint();
        }

    }

    // mus events:
    @Override
    public void mouseClicked(MouseEvent e) {
        // udregn koordinater tilsvarende til minesweeper boardet:
       int[] cords = {(e.getX()-x_cord)/Tile_Size, (e.getY()-y_cord)/Tile_Size};

        // Lav et nyt board første gang der trykkes på GUI:
       if(Minesweeper == null){
           initializeBoard(cords);
       }
       // reveal eller plant flag alt efter om det er venstre/højre klik på musen:
       else{
           if(e.getButton() == MouseEvent.BUTTON1){
               Minesweeper.press(cords);

               if (Minesweeper.lossCheck(cords)) {
                   repaint();
                   JOptionPane.showMessageDialog(null, "Du har tabt");
                   Minesweeper = null;
               }
           }
           else if(e.getButton() == MouseEvent.BUTTON3){
               Minesweeper.setFlag(cords);

               if(Minesweeper.winCheck()){
                   repaint();
                   JOptionPane.showMessageDialog(null, "Du har vundet");
                   Minesweeper = null;

               }
           }
       }

        repaint();
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
