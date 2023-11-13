package aes.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//	ImageRead class draws image panel
// as well as override the paint component which is called automatically in background
public class ImageRead extends JPanel {

    private BufferedImage image;

    public ImageRead() {
        this.image = null;

        setFocusable(true);

        setLayout(null);
        setOpaque(true);

    }

    /**
     * returns the image
     * image geter methd
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Sets the main Image
     **/
    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    /**
     * Overwriting paint event for drawing
     * <p>
     * the paintComponent is called internally when images change positions by
     * resizing of jframe or changing focus. No method call for PaintComponent.
     **/
    public void paintComponent(Graphics g) {
        g.setColor(new Color(34, 33, 33));
        g.fillRect(0, 0, getSize().width, getSize().height);

        if (image != null) {

            int center_x = getSize().width / 2 - image.getWidth() / 2;
            int center_y = getSize().height / 2 - image.getHeight() / 2;

            if (center_x < 10) {
                center_x = 10;
            }
            if (center_y < 10) {
                center_y = 10;
            }

            g.drawImage(image, center_x, center_y, null);
        }
    }
}
