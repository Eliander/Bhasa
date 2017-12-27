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

    private Utilities utility = new Utilities();
    public void drawImage(Timetable timetable) throws Exception {
        try {
            int width = 800;
            int base = 100;
            
            BufferedImage bi = new BufferedImage(width, 100 * (timetable.getCourses().size() + 1), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = bi.createGraphics();
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 12));
            

            for (int i = 0; i < timetable.getCourses().size(); i++) {

                String message = timetable.getCourses().get(i).getStart() + " - " + timetable.getCourses().get(i).getEnd();
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, base, 100, 100);
                g2d.setPaint(Color.black);
                g2d.drawString(message, 15, base + 30);
                base += 100;
            }
            base = 100;
            for (int i = 0; i < timetable.getCourses().size(); i++) {
                g2d.setColor(utility.randHEXColor());
                String message = timetable.getCourses().get(i).getLabel();
                g2d.fillRect(100, base, width, 100);
                g2d.setPaint(Color.black);
                g2d.drawString(message, 150, base + 30);
                message = timetable.getCourses().get(i).getStart() + " - " + timetable.getCourses().get(i).getEnd();
                g2d.drawString(message, 150, base + 30 + g2d.getFontMetrics().getHeight());
                message = timetable.getCourses().get(i).getClassroom();
                g2d.drawString(message, 150, base + 30 + 2 * (g2d.getFontMetrics().getHeight()));
                base += 100;
            }

            g2d.setColor(Color.white);//serve per settare lo sfondo a bianco
            g2d.fillRect(0, base, width, 100);
            //to-do salvare nel path relativo al corso/giorno
            File file = new File("..\\" + utility.nameProject + "\\src\\main\\resources\\" + timetable.getGraduation() + "\\orario.png");
            file.mkdirs();
            ImageIO.write(bi, "PNG", file);
            //to-do aggiungere l'orario della lezione di lato

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    
}
