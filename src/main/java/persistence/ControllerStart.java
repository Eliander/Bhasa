package persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 *
 * @author Elia
 */
public class ControllerStart extends Controller {

    private static final Logger log = LogManager.getLogger(ControllerStart.class);

    public ControllerStart() {
        super("/start");
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
        return "Ciao, benvenuto nel bot orari UNIVR. Seleziona il tuo corso di laurea: ";
    }

}
