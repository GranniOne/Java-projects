package DrawingRust;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImagePanel extends JPanel implements ActionListener {
    private BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
    float opacity = 0.5f;

    public ImagePanel() {
        timer.start();
        setImage();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            // Draw the image in the panel
            Graphics2D g2d = (Graphics2D) g.create();
            // Set the opacity
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            // Draw the image
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            g2d.dispose();
        }
    }

    public void setImage() {
        try {
            FileDialog fileDialog = new FileDialog((Frame) null, "Select File");
            fileDialog.setVisible(true);
            String dir = fileDialog.getDirectory();
            String file = fileDialog.getFile();

            if (file != null) {
                File selectedFile = new File(dir, file);
                BufferedImage newImage = ImageIO.read(selectedFile);

                if (newImage != null) {
                    image = newImage; // Update only if successfully loaded
                    Main.frame.setSize(image.getWidth(), image.getHeight());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    Timer timer = new Timer(1000, e -> repaint());



    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
