/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c2ontroller;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 *
 * @author Elia
 */
public class ControllerError extends Controller {

    public ControllerError() {
        super("");
    }

    @Override
    public boolean send(long chatId, DefaultAbsSender bot) {
        SendMessage message = new SendMessage(chatId, this.text);
        try {
            bot.execute(message);
            return true;
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    protected String setText() {
        return "Hai inserito un comando non valido";
    }

}
