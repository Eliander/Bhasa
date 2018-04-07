package controller;

import org.telegram.telegrambots.bots.DefaultAbsSender;

/**
 *
 * @author Elia
 */
public abstract class Controller {
    
    protected String command;
    protected String text;

    public Controller(String command) {
        this.command = command;
        this.text = setText();
    }

    public String getCommand() {
        return command;
    }
    
    public abstract boolean send(long chatId, DefaultAbsSender bot);
    
    protected abstract String setText();
    
}
