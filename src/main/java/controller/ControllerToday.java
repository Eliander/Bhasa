/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bhasa.MainBot;
import java.util.Calendar;
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
public class ControllerToday extends Controller {

    Calendar date;

    public ControllerToday() {
        super(Utilities.TODAY_COMMAND);
    }

    @Override
    public boolean send(long chatId, DefaultAbsSender bot) {
        SendMessage message = new SendMessage();
        try {
            message.setChatId(chatId);
            message.setText(getTodayTime(chatId));
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
        
        /*date = Calendar.getInstance();
        date.set(2018, 3, 8);*/
        return "Orario per il giorno " + date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR);
    }

    private String getTodayTime(long chatId) {
        int graduation = MainBot.dao.getGraduationDAO().getGraduation(chatId + "");
        String year = MainBot.dao.getGraduationDAO().getYear(chatId + "");
        String result = this.text + "\n";
        
        //non sempre vale la pena controllare, se e sabato o domenica non ci sono lezioni
        if(date.get(Calendar.DAY_OF_WEEK) == 1 || date.get(Calendar.DAY_OF_WEEK) == 7){
            result += "UNIVR e' chiusa!";
            return result;
        }
        
        UNIVRequest call = new UNIVRequest();
        Timetable timetable = call.getData(graduation + "", year, date);
        if(timetable.getLessons().size() == 0){
            return "Oggi non hai lezione";
        }
        for(Lesson lesson : timetable.getLessons()){
            result = result + lesson.getLabel() + "\n";
            result = result + lesson.getClassroom() + "\n";
            result = result + lesson.getStart() + " - " + lesson.getEnd() + "\n" + "\n";
            
        }
        return result;
    }

}
