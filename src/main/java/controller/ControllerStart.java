package controller;

import bhasa.MainBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import utilities.Utilities;

/**
 *
 * @author Elia
 */
public class ControllerStart extends Controller {

    private static final Logger LOG = LogManager.getLogger(ControllerStart.class);

    public ControllerStart() {
        super(Utilities.START_COMMAND);
    }

    @Override
    public boolean send(long chatId, DefaultAbsSender bot) {
        //new user
        if (MainBot.dao.getGraduationDAO().getGraduation(chatId + "") == 0) {
            SendMessage message = new SendMessage(chatId, this.text);
            MainBot.dao.getCommandDAO().insertLastCommand(chatId + "", "/setGraduation");
            try {
                bot.execute(message);
                return true;
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
                return false;
            }
        } //old user
        else {
            SendMessage message = new SendMessage(chatId, "Bentornato");
            try {
                bot.execute(message);
                return true;
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    @Override
    protected String setText() {
        return "Ciao, benvenuto nel bot orari UNIVR. Scrivi il tuo corso di laurea: " + "\n" + "es: informatica ";
    }

}
