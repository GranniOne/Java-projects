package DrawingRust;


import javax.swing.*;


public class Main {
    public static WindowsApiCall frame = new WindowsApiCall();

    public static void main(String[] args) {



        // Set up the JFrame
        frame.setUndecorated(true);
        //frame.setOpacity(0.5f); // Set transparency
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(new ImagePanel());
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        frame.Running();




        /*
        // Add MouseListener to handle dragging
        Point initialClick = new Point();
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(isLocked) return;
                initialClick.setLocation(e.getPoint());
            }
        });
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(isLocked) return;
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;
                int newX = thisX + e.getX() - initialClick.x;
                int newY = thisY + e.getY() - initialClick.y;
                frame.setLocation(newX, newY);
            }
        });

         */

        /*
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    // Toggle click-through mode when F1 is pressed
                    isLocked = !isLocked;
                    toggleClickThrough();
                }
                if(e.getKeyCode() == KeyEvent.VK_F2) {
                    // Toggle transparency when F2 is pressed
                    isTransparent = !isTransparent;
                    if(!isTransparent) {
                        frame.setOpacity(0.5f);
                    } else {
                        frame.setOpacity(0f);
                    }
                }
            }
        });

         */

        // Make the frame visible
        //frame.setVisible(true);




    }


    // Check if the current platform is Windows

}
