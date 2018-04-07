/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bhasa.MainBot;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import persistence.Lesson;
import persistence.Timetable;
import postRequest.UNIVRequest;
import utilities.Utilities;

/**
 *
 * @author Elia
 */
public class ControllerTomorrow extends Controller {

    Calendar date;

    public ControllerTomorrow() {
        super(Utilities.tomorrow);
    }

    @Override
    public boolean send(long chatId, DefaultAbsSender bot) {
        SendMessage message = new SendMessage();
        try {
            message.setChatId(chatId);
            message.setText(getTomorrowTime(chatId));
            bot.execute(message);
            return true;
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    protected String setText() {
        
        date = Calendar.getInstance();
        date.set(GregorianCalendar.DAY_OF_MONTH, date.get(GregorianCalendar.DAY_OF_MONTH) + 1);
        /*date = Calendar.getInstance();
        date.set(2018, 3, 8);*/
        return "Orario per il giorno " + date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR);
    }

    private String getTomorrowTime(long chatId) {
        int graduation = MainBot.dao.getGraduationDAO().getGraduation(chatId + "");
        String course = MainBot.dao.getGraduationDAO().getCourse(chatId + "");
        String result = this.text + "\n";
        
        //non sempre vale la pena controllare, se e sabato o domenica non ci sono lezioni
        if(date.get(Calendar.DAY_OF_WEEK) == 1 || date.get(Calendar.DAY_OF_WEEK) == 7){
            result += "UNIVR e' chiusa!";
            return result;
        }
        
        UNIVRequest call = new UNIVRequest();
        Timetable timetable = call.getData(graduation + "", course, date);
        for(Lesson lesson : timetable.getLessons()){
            result = result + lesson.getLabel() + "\n";
            result = result + lesson.getClassroom() + "\n";
            result = result + lesson.getStart() + " - " + lesson.getEnd() + "\n" + "\n";
            
        }
        return result;
    }

}
