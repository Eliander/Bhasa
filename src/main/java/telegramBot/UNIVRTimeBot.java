/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegramBot;

import bhasa.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import persistence.Controller;
import persistence.ControllerStart;

/**
 *
 * @author Elia
 */
public class UNIVRTimeBot extends TelegramLongPollingBot {
    
    private final String start_command = "/start";


    private static final Logger log = LogManager.getLogger(UNIVRTimeBot.class);

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().getText() != null){
            long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            switch(message){
                case start_command:
                    Controller controller = new ControllerStart();
                    controller.send(chatId, this);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "UNIVRTimeBot";
    }

}
