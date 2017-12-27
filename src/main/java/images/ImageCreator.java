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
import java.util.Random;
import javax.imageio.ImageIO;
import persistence.Timetable;

/**
 *
 * @author Elia
 */
public class ImageCreator {

    public void drawImage(Timetable timetable) throws Exception {
        try {
            int width = 800;
            int base = 100;

            // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
            // into integer pixels
            BufferedImage bi = new BufferedImage(width, 100 * (timetable.getCourses().size() + 1), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = bi.createGraphics();
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 12));
            g2d.setBackground(Color.WHITE);
            for (int i = 0; i < timetable.getCourses().size(); i++) {
                g2d.setColor(randomColor());
                String message = timetable.getCourses().get(i).getLabel();
                g2d.fillRect(0, base, width, 100);
                g2d.setPaint(Color.black);
                g2d.drawString(message, 50, base + 30);
                message = timetable.getCourses().get(i).getStart() + " - " + timetable.getCourses().get(i).getEnd();
                g2d.drawString(message, 50, base + 30 + g2d.getFontMetrics().getHeight());
                message = timetable.getCourses().get(i).getClassroom();
                g2d.drawString(message, 50, base + 30 + 2*(g2d.getFontMetrics().getHeight()));
                base += 100;
            }
            g2d.setColor(Color.white);//serve per settare lo sfondo a bianco
            g2d.fillRect(0, base, width, 100);
            //to-do salvare nel path relativo al corso/giorno
            ImageIO.write(bi, "PNG", new File("D:\\Users\\Elia\\Documents\\NetBeansProjects\\Bhasa\\src\\main\\resources\\orario.png"));
            //to-do aggiungere l'orario della lezione di lato
            
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private Color randomColor() {
        int r, g, b;
        Random random = new Random();
        r = random.nextInt(256);
        g = random.nextInt(256);
        b = random.nextInt(256);
        return new Color(r, g, b);
    }
}
