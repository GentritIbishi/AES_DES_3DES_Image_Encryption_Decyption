package aes.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCrop {
    public static BufferedImage cropImage(BufferedImage originalImage, int x, int y, int width, int height) {
        return originalImage.getSubimage(x, y, width, height);
    }
}
