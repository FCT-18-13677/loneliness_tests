package es.uji.giant.DialogFlowTests.services;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextToPNG {

    private static String[] strings;

    TextToPNG() {
        strings = new String[10];
        strings[0] = "Me encunetro muy bien";
        strings[1] = "Me he comprado una bicicleta para poder ir a la playa en verano";
        strings[2] = "La última película de los vengadores está muy chula";
        strings[3] = "Everyone was busy, so I went to the movie alone.";
        strings[4] = "Malls are great places to shop; I can find everything I need under one roof.";
        strings[5] = "Should we start class now, or should we wait for everyone to get here?";
        strings[6] = "Two seats were vacant. One day in my life I go to my uncle's field to get some oranges in order to give my children a nice meal. Lamao";
        strings[7] = "Yeah, I think it's a good environment for learning English.";
        strings[8] = "Last Friday in three week’s time I saw a spotted striped blue worm shake hands with a legless lizard.";
        strings[9] = "How was the math test?";

        separateLines();
    }

    private void separateLines() {
        // Create new line every 10 blanks.
        for (int j = 0; j < strings.length; j++) {
            String newString = "";
            int blancks = 0;
            for (int i = 0; i < strings[j].length(); i++) {
                newString += strings[j].charAt(i);
                if (strings[j].charAt(i) == ' ') {
                    blancks++;
                    if (blancks % 10 == 0) {
                        newString += '\n';
                    }
                }
            }
            strings[j] = newString;
        }
    }


    public static void generatePNGs() {
        for (int i = 0; i < strings.length; i++) {
            String text = strings[i];
            String[] separatedLines = text.split("\n");
            String maxWidthLine = getMaxWidth(separatedLines);

            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();
            Font font = new Font("Arial", Font.PLAIN, 36);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int width = fm.stringWidth(maxWidthLine);
            int height = fm.getHeight() * separatedLines.length;
            g2d.dispose();

            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = img.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2d.setFont(font);
            fm = g2d.getFontMetrics();
            g2d.setColor(Color.BLACK);

            for (int j = 0; j < separatedLines.length; j++)
                g2d.drawString(separatedLines[j], 0, fm.getAscent() + (j * fm.getAscent()));
            g2d.dispose();

            try {
                ImageIO.write(img, "png", new File("PNG_" + i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getMaxWidth(String[] separatedLines) {
        String maxWidthLine = "";

        for (String s : separatedLines) {
            if (s.length() > maxWidthLine.length())
                maxWidthLine = s;
        }
        return maxWidthLine;
    }

    public static void main(String[] args) {
        TextToPNG t2p = new TextToPNG();
        t2p.generatePNGs();
    }
}
