package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class ImplTextGraphicsConverter implements TextGraphicsConverter{
    private int maxWidth;
    private int maxHeight;
    private double maxRatio;
    private TextColorSchema schema = new ImplTextColorSchema();
    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        int width = img.getWidth();
        int height = img.getHeight();
        double wr = width;
        double hr = height;
        double ratio = wr / hr;
        double ratio2 = hr / wr;
        if (maxRatio < ratio || maxRatio < ratio2) {
            throw new BadImageSizeException(ratio, maxRatio);
        }
        if (width > maxWidth && maxWidth > 0) {
            double wd = width;
            double mwd = maxWidth;
            double hd = height;
            double dif = wd / mwd;
            hd = hd / dif;
            height = (int) Math.round(hd);
            width = maxWidth;


        }

        if (height > maxHeight && maxHeight > 0) {
            double hd = height;
            double mhd = maxHeight;
            double wd = width;
            double dif = hd / mhd;
            wd = wd / dif;
            width = (int) Math.round(wd);
            height = maxHeight;

        }
        Image scaledImage = img.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);

        WritableRaster bwRaster = bwImg.getRaster();
        char cc [][] = new char[width][height];

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                cc[w][h] = c;
            //запоминаем символ c, например, в двумерном массиве или как-то ещё на ваше усмотрение
            }
        }
//        String picture = "";
        StringBuilder p = new StringBuilder();
        for (int h = 0; h < height; h++){
            for (int w = 0; w < width+1; w++) {
                if (w == width){
//                    picture = picture + "\n";
                    p.insert(p.length(), "\n");
                }else {
                    char e = cc[w][h];
                    String ee = Character.toString(e);
//                    picture = picture + ee + ee;
                    p.insert(p.length(), ee + ee);
                }

            }
        }
        String picture = p.toString();
        return picture;
    }

    @Override
    public void setMaxWidth(int width) {
         maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public double getMaxRatio() {
        return maxRatio;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public TextColorSchema getSchema() {
        return schema;
    }
}
