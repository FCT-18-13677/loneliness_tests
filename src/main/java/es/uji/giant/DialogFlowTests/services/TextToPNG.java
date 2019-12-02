package es.uji.giant.DialogFlowTests.services;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import es.uji.giant.DialogFlowTests.model.Questionnarie;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TextToPNG {

    private static String[] strings;

    TextToPNG() {
        List<Questionnarie> questionnaries = getQuestionnariesFromREST();
        strings = new String[questionnaries.size()];

        for (int i = 0; i < questionnaries.size(); i++)
            strings[i] = questionnaries.get(i).getUserComments();

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

    public static java.util.List<Questionnarie> getQuestionnariesFromREST() {
        try {
            URL urlForGetRequest = new URL("https://inti.init.uji.es:8463/getQuestionnaries");

            String readLine = null;

            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();

            conection.setRequestMethod("GET");

            //conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here

            int responseCode = conection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(

                        new InputStreamReader(conection.getInputStream()));

                StringBuffer response = new StringBuffer();

                while ((readLine = in .readLine()) != null) {

                    response.append(readLine);

                } in .close();

                Type listType = new TypeToken<ArrayList<Questionnarie>>(){}.getType();
                List<Questionnarie> questionnaries = new Gson().fromJson(response.toString(), listType);

                return questionnaries;

            } else {

                System.out.println("GET NOT WORKED");
                System.out.println(responseCode);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        TextToPNG t2p = new TextToPNG();
        t2p.generatePNGs();
    }
}
