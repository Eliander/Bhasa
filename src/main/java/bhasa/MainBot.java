/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bhasa;

import d2ao.DAOSettings;
import java.util.HashMap;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import postRequest.UNIVRequest;
import telegramBot.UNIVRTimeBot;

/**
 *
 * @author Elia
 */
public class MainBot {

    public static final DAOSettings dao = DAOSettings.DAO;
    public static final UNIVRequest request = new UNIVRequest();
    public static HashMap<Integer, HashMap<String, String>> years = new HashMap();
    //TO DO girala, cerchi per nome non per valore
    public static HashMap<Integer, String> graduations = getGraduation();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new UNIVRTimeBot());
        } catch (TelegramApiRequestException ex) {
            ex.printStackTrace();
        }
    }

    private static HashMap<Integer, String> getGraduation() {
        try {
            String value = request.getValues()[1];
            //remove all lessons
            value = value.replace("[", "$");
            value = value.replace("[", "$");
            //value = value.replaceAll("\\[.*?\\]", "");
            value = value.replaceAll("\"", "");
            value = value.replaceAll(":", "");
            String[] split = value.split("label");
            //extract graduations
            HashMap<Integer, String> graduations = new HashMap();
            for (int i = 0; i < split.length; i++) {
                int graduation = 0;
                if ((split[i].contains("Laurea"))) {
                    graduation = Integer.parseInt(split[i].split("valore")[1].substring(0, 3));
                    graduations.put(graduation, split[i].split(",")[0]);
                    i++;
                    //prendo i corsi
                    HashMap<String, String> gradYears = new HashMap();
                    while (!(split[i].contains("Laurea") || split[i].contains("CLA"))) {
                        int offset = split[i].indexOf("valore") + "valore".length();
                        int end = split[i].indexOf("}");
                        System.out.println(graduation + " " + offset + " " + end);
                        gradYears.put(split[i].substring(offset, end), split[i].substring(0, offset - 6));
                        //years.put(split[i]);
                        i++;
                    }
                    years.put(graduation, gradYears);
                    i--;
                }

            }
        }catch(Exception e){
            System.out.println(e);
        }
        return graduations;
    }
}
