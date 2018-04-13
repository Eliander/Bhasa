package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Elia
 */
public class CommandDAO {
    
    private final String SELECT = "SELECT * FROM COMMANDS WHERE CHATID = ?";
    private final String INSERT = "INSERT INTO COMMANDS (CHATID, LASTCOMMAND) values (?, ?)";
    private final String UPDATE = "UPDATE COMMANDS SET LASTCOMMAND = ? WHERE CHATID = ?";
    private final String DELETE = "DELETE FROM COMMANDS WHERE CHATID = ?";
    
    public String getLastCommand(String chatID){
        String command = "";
        try {
            Connection con = DAOSettings.getConnection();
            PreparedStatement pst = con.prepareStatement(SELECT);
            pst.setString(1, chatID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                command = rs.getString("LASTCOMMAND");
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return command;
    }
    //to do inserire un numero davanti al nome della laurea nella mappa statica per riconoscere in caso di simili con nomi lunghi
    //to do quando cambi laurea metti il corso a null. se si prova a chiamare today senza aver settato l'anno manda errore
    public boolean insertLastCommand(String chatID, String lastCommand){
        boolean result = false;
        try {
            Connection con = DAOSettings.getConnection();
            PreparedStatement pst = con.prepareStatement(INSERT);
            pst.setString(1, chatID);
            pst.setString(2, lastCommand);
            pst.executeUpdate();
            con.close();
            result = true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }
    
    public boolean updateLastCommand(String chatID, String lastCommand){
        boolean result = false;
        try {
            Connection con = DAOSettings.getConnection();
            PreparedStatement pst = con.prepareStatement(UPDATE);
            pst.setString(1, lastCommand);
            pst.setString(2, chatID);
            pst.executeUpdate();
            con.close();
            result = true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }
    
    public boolean clearLastCommand(String chatID){
        boolean result = false;
        try {
            Connection con = DAOSettings.getConnection();
            PreparedStatement pst = con.prepareStatement(UPDATE);
            pst.setString(1, "");
            pst.setString(2, chatID);
            pst.executeUpdate();
            con.close();
            result = true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }
    
    public boolean deleteLastCommand(String chatID){
        boolean result = false;
        try {
            Connection con = DAOSettings.getConnection();
            PreparedStatement pst = con.prepareStatement(DELETE);
            pst.setString(1, chatID);
            pst.executeUpdate();
            con.close();
            result = true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }
    
}
