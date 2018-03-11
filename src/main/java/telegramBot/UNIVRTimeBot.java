/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegramBot;

import bhasa.MainOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import Controller.Controller;
import Controller.ControllerError;
import Controller.ControllerSelectGraduation;
import Controller.ControllerStart;

/**
 *
 * @author Elia
 */
public class UNIVRTimeBot extends TelegramLongPollingBot {

    private final String start_command = "/start";
    private final String set_graduation = "/setGraduation";

    private static final Logger log = LogManager.getLogger(UNIVRTimeBot.class);

    @Override
    public String getBotToken() {
        return "532821188:AAGjdsB7s_ZG_aZf-mYhVsNv77NnHMih1rg";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().getText() != null) {
            long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            Controller controller;
            if (message.contains(start_command)) {
                controller = new ControllerStart();
            } else if (message.contains(set_graduation)) {
                if (message.equals(set_graduation)) {
                    controller = new ControllerSelectGraduation();
                }else{
                    controller = new ControllerSelectGraduation(message.replace(set_graduation, ""));
                }
            } else {
                //hai inserito un comando non valido
                controller = new ControllerError();
            }

            controller.send(chatId, this);
        }
    }

    @Override
    public String getBotUsername() {
        return "UNIVRTimeBot";
    }

}
