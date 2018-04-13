package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Elia
 */
public class GraduationDAO {

    private final String SELECT = "SELECT * FROM GRADUATION WHERE CHATID = ?";
    //to do: modificare il campo nel db
    private final String SELECT_YEAR = "SELECT COURSE FROM GRADUATION WHERE CHATID = ?";
    private final String INSERT = "INSERT INTO GRADUATION (CHATID, GRADUATION) values (?, ?)";
    private final String UPDATE_YEAR = "UPDATE GRADUATION SET COURSE = ? WHERE CHATID = ?";
    private final String UPDATE = "UPDATE GRADUATION SET GRADUATION = ? WHERE CHATID = ?";
    private final String DELETE = "DELETE FROM GRADUATION WHERE CHATID = ?";

    public int getGraduation(String chatID) {
        int graduation = 0;
        try {
            Connection con = DAOSettings.getConnection();
            PreparedStatement pst = con.prepareStatement(SELECT);
            pst.setString(1, chatID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                graduation = rs.getInt("GRADUATION");
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return graduation;
    }

    public String getYear(String chatID) {
        String year = "";
        try {
            Connection con = DAOSettings.getConnection();
            PreparedStatement pst = con.prepareStatement(SELECT_YEAR);
            pst.setString(1, chatID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                //to do modificare anche qui
                year = rs.getString("COURSE");
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return year;
    }

    public boolean insertUser(String chatID, String graduation) {
        boolean result = false;
        try {
            Connection con = DAOSettings.getConnection();
            PreparedStatement pst = con.prepareStatement(INSERT);
            pst.setString(1, chatID);
            pst.setInt(2, Integer.parseInt(graduation));
            pst.executeUpdate();
            con.close();
            result = true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }

    public boolean insertYear(String chatID, String year) {
        boolean result = false;
        try {
            Connection con = DAOSettings.getConnection();
            PreparedStatement pst = con.prepareStatement(UPDATE_YEAR);
            pst.setString(1, year);
            pst.setString(2, chatID);
            pst.executeUpdate();
            con.close();
            result = true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }

    public boolean updateUser(String chatID, String graduation) {
        boolean result = false;
        try {
            Connection con = DAOSettings.getConnection();
            PreparedStatement pst = con.prepareStatement(UPDATE);
            pst.setString(1, chatID);
            pst.setInt(2, Integer.parseInt(graduation));
            pst.executeUpdate();
            con.close();
            result = true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }

    public boolean deleteUser(String chatID) {
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
