package aes.utils;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static BufferedImage loadImage(String filePath) throws IOException {
        return ImageIO.read(new File(filePath));
    }

    public static void displayImage(String title, BufferedImage image) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void saveImage(String filePath, BufferedImage image) throws IOException {
        ImageIO.write(image, "bmp", new File(filePath));
    }

    public static byte[] imageToBytes(BufferedImage image) throws IOException {
        Image tmp = image.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT);
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(tmp, 0, 0, null);

        int[] pixels = new int[image.getWidth() * image.getHeight()];
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixels, 0, bufferedImage.getWidth());

        byte[] result = new byte[pixels.length * 3]; // Assuming 3 bytes per pixel (RGB)
        int index = 0;

        for (int pixel : pixels) {
            result[index++] = (byte) ((pixel >> 16) & 0xFF); // Red
            result[index++] = (byte) ((pixel >> 8) & 0xFF);  // Green
            result[index++] = (byte) (pixel & 0xFF);         // Blue
        }

        return result;
    }

    public static BufferedImage bytesToImage(byte[] bytes, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int[] pixels = new int[width * height];
        int index = 0;

        for (int i = 0; i < pixels.length && index < bytes.length - 2; i++) {
            int r = bytes[index++] & 0xFF;
            int g = bytes[index++] & 0xFF;
            int b = bytes[index++] & 0xFF;

            pixels[i] = (r << 16) | (g << 8) | b;
        }

        image.setRGB(0, 0, width, height, pixels, 0, width);
        return image;
    }

}
