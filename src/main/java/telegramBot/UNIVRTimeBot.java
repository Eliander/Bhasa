/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegramBot;

import controller.ControllerStart;
import controller.ControllerError;
import controller.ControllerHome;
import controller.Controller;
import controller.ControllerToday;
import controller.ControllerSelectGraduation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import bhasa.MainBot;
import controller.ControllerSelectYear;
import controller.ControllerTomorrow;
import utilities.Utilities;

/**
 *
 * @author Elia
 */
public class UNIVRTimeBot extends TelegramLongPollingBot {

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
            //to do inserire una risposta a grazie
            switch (message) {
                case Utilities.START_COMMAND:
                    controller = new ControllerStart();
                    break;
                case Utilities.SET_GRADUATION_COMMAND:
                    controller = new ControllerSelectGraduation();
                    break;
                case Utilities.SET_YEAR_COMMAND:
                    controller = new ControllerSelectYear();
                    break;
                case Utilities.HOME_COMMAND:
                    controller = new ControllerHome();
                    break;
                case Utilities.TODAY_COMMAND:
                    controller = new ControllerToday();
                    break;
                case Utilities.TOMORROW_COMMAND:
                    controller = new ControllerTomorrow();
                    break;
                default:
                    controller = selectController(chatId, message);
            }
            controller.send(chatId, this);
        }
    }

    @Override
    public String getBotUsername() {
        return "UNIVRTimeBot";
    }

    /*
    *
    * Telegram non supporta le sessioni. Per risolvere ogni volta che uso un comando che richiede l'interazione
    * con l'utente, lo salvo su db. Quando ricevo una risposta che non corrisponde a nessuno dei comandi vado a 
    * vedere su db qual era l'ultimo comando inserito, questo mi indica che l'opzione inviata corrisponde alla risposta
    * dell'utente. Posso gestire cosi il tutto
    *
     */
    private Controller selectController(long chatID, String message) {
        Controller controller = null;
        String lastCommand = MainBot.dao.getCommandDAO().getLastCommand(chatID + "");
        switch (lastCommand) {
            case Utilities.SET_GRADUATION_COMMAND:
                controller = new ControllerSelectGraduation(message);
                break;
            case Utilities.SET_YEAR_COMMAND:
                controller = new ControllerSelectYear(message);
                break;
            default:
                //hai inserito un comando o un'opzione non valido
                controller = new ControllerError();
        }
        return controller;
    }

}
