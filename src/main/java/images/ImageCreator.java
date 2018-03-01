/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import persistence.Timetable;
import utilities.Utilities;

/**
 *
 * @author Elia
 */
public class ImageCreator {

    private final Utilities utility = new Utilities();
    private final int heightForElement = 45;

    public void drawImage(Timetable timetable) throws Exception {
        try {
            int width = 500;
            int widthForTime = 500;
            int offsetForTime = 10;
            int base = heightForElement;

            BufferedImage bi = new BufferedImage(width, 1045, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = bi.createGraphics();
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 12));

            boolean isHalf = false;
            int hour = 8;
            //left side bar
            for (int i = 0; i < 22; i++) {
                //String message = timetable.getCourses().get(i).getStart() + " - " + timetable.getCourses().get(i).getEnd();
                String message = "" + hour;
                if (isHalf) {
                    isHalf = false;
                    hour++;
                    message += ".30";
                } else {
                    isHalf = true;
                    message += ".00";
                }
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, base, widthForTime, heightForElement);
                g2d.setPaint(Color.black);
                g2d.drawString(message, offsetForTime, base + 30);
                base += heightForElement;
            }
            base = heightForElement;
            //data
            g2d = fillData(timetable, g2d);

            if (timetable.getCourses().size() != 0) {
                //to-do salvare nel path relativo al corso/giorno
                File file = new File("..\\" + utility.NAME_PROJECT + "\\src\\main\\resources\\" + timetable.getGraduation() + "\\orario.png");
                file.mkdirs();
                ImageIO.write(bi, "PNG", file);
                //to-do aggiungere l'orario della lezione di lato
            }
            //se e vuota non serve a niente fare l'orario
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private int calcHeight(int elements) {
        return heightForElement * (elements + 1);
    }

    private int holeNumber(String string) {
        String[] split = string.split(":");
        int value = Integer.parseInt(split[0]);
        int hole = value - 8;
        hole = hole * 2;
        if (split[1].equals("30")) {
            hole++;
        }
        return ++hole;
    }

    private Graphics2D fillData(Timetable timetable, Graphics2D g2d) {
        int width = 500;
        for (int i = 0; i < timetable.getCourses().size(); i++) {
            
            int base = holeNumber(timetable.getCourses().get(i).getStart())*heightForElement;
            int end = holeNumber(timetable.getCourses().get(i).getEnd())*heightForElement;
            
            g2d.setColor(utility.randHEXColor());
            String message = timetable.getCourses().get(i).getLabel();
            g2d.fillRect(heightForElement, base, width, end - base);
            g2d.setPaint(Color.black);
            g2d.drawString(message, 125, holeNumber(timetable.getCourses().get(i).getStart())*heightForElement + 30);
            message = timetable.getCourses().get(i).getClassroom();
            g2d.drawString(message, 125, holeNumber(timetable.getCourses().get(i).getStart())*heightForElement + 40 + (g2d.getFontMetrics().getHeight()));
        }
        return g2d;
    }

}
