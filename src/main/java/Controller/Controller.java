package Controller;

import org.telegram.telegrambots.bots.DefaultAbsSender;

/**
 *
 * @author Elia
 */
public abstract class Controller {
    
    protected String command;
    protected String text;
    protected String option;

    public Controller(String command) {
        this.command = command;
        this.text = setText();
    }
    
    public Controller(String command, String option){
        this.command = command;
        this.text = setText();
        this.option = option;
    }

    public String getCommand() {
        return command;
    }
    
    public abstract boolean send(long chatId, DefaultAbsSender bot);
    
    protected abstract String setText();
    
}
