package images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;
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
            int offsetForTime = 10;
            int base = heightForElement;

            BufferedImage bi = new BufferedImage(width, 1045, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = bi.createGraphics();
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));

            boolean isHalf = false;
            int hour = 8;
            //left side bar
            for (int i = 0; i < 22; i++) {
                //String message = timetable.getlessons().get(i).getStart() + " - " + timetable.getlessons().get(i).getEnd();
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
                g2d.fillRect(0, base, width, heightForElement);
                g2d.setPaint(Color.black);
                g2d.drawString(message, offsetForTime, base + 30);
                base += heightForElement;
            }
            base = heightForElement;
            //data
            g2d = fillData(timetable, g2d);

            if (timetable.getLessons().size() != 0) {
                /*
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(bi, "PNG", os);
                InputStream is = new ByteArrayInputStream(os.toByteArray());
                */
                File file = new File("..\\" + utility.NAME_PROJECT + "\\src\\main\\resources\\" + timetable.getGraduation() + "\\orario.png");
                file.mkdirs();
                ImageIO.write(bi, "PNG", file);
            }
            //se e vuota non serve a niente fare l'orario
        } catch (IOException ie) {
            ie.printStackTrace();
        }
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
        for (int i = 0; i < timetable.getLessons().size(); i++) {

            int base = holeNumber(timetable.getLessons().get(i).getStart()) * heightForElement;
            int end = holeNumber(timetable.getLessons().get(i).getEnd()) * heightForElement;
            g2d.setColor(utility.randHEXColor());
            String message = timetable.getLessons().get(i).getLabel();
            g2d.fillRect(heightForElement, base, width, end - base);
            g2d.setPaint(Color.black);
            g2d.drawString(message, 75, holeNumber(timetable.getLessons().get(i).getStart()) * heightForElement + 30);
            message = timetable.getLessons().get(i).getClassroom();
            g2d.drawString(message, 75, holeNumber(timetable.getLessons().get(i).getStart()) * heightForElement + 40 + (g2d.getFontMetrics().getHeight()));
            message = timetable.getLessons().get(i).getStart() + " - " + timetable.getLessons().get(i).getEnd();
            g2d.drawString(message, 75, holeNumber(timetable.getLessons().get(i).getStart()) * heightForElement + 65 + (g2d.getFontMetrics().getHeight()));
        }
        return g2d;
    }

}
