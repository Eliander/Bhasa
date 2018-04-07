package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Elia
 */
public class ImagesDAO {
    
    private final String SELECT = "SELECT TELEGRAMIMAGEID, DATE FROM IMAGES WHERE GRADUATION = ?";
    private final String UPDATE = "UPDATE IMAGES SET TELEGRAMIMAGEID = ? date = ? WHERE GRADUATION = ?";
    //to-do update che chiama imageCreator che salva un inputStream;
    
    public String getTelegramCode(int graduation){
        try {
            Connection con = DAOSettings.getConnection();
            PreparedStatement pst = con.prepareStatement(SELECT);
            pst.setInt(1, graduation);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String imageId = rs.getString("TELEGRAMIMAGEID");
                Date date = rs.getDate("DATE");
                if(isValidDate(date)){
                    return imageId;
                }else{
                    return "";
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public boolean updateImage(){
        return true;
    }
    
    private boolean isValidDate(Date date){
        java.util.Date today = new java.util.Date();
        if(date.getTime() < (today.getTime() - (1000 * 60 * 60 * 24))){ //24 ore fa
            return false;
        }
        return true;
    }
    
}
