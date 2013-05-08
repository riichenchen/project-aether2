package Database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shiyang
 */
public class DatabaseHandler {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String sqluser = "aetherServer";
    private String sqluserpw = "15201599298";
    public DatabaseHandler(){
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                .getConnection("jdbc:mysql://24.57.115.97/projectaether2?"
                    + "user="+sqluser+"&password="+sqluserpw);
            statement = connect.createStatement();
            System.out.println("Successfully Connected to Database!");
        } catch (Exception e) {
            close();
            System.out.println("Error: "+e.getMessage());
        } 
    }
    /*Two method tools that are used for SHA hashing for password security and what not*/
    public static String SHAsum(String inputString){
        try {
            byte[] convertme = inputString.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-1"); 
            return byteArray2Hex(md.digest(convertme));
        } catch (NoSuchAlgorithmException e){
            System.out.println("Error Sha1-ing");
            return null;
        }
    }

    private static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
    public ResultSet makeQuerry(String querry){
        try {
          resultSet = connect.prepareStatement(querry).executeQuery();
          return resultSet;
        } catch (Exception e){
            close();
            System.out.println("Error querrying: "+e.getMessage());
            return null;
        } 
    }
  
    public boolean makeUpdate(String update){
        try {
            connect.prepareStatement(update).executeUpdate();
            return true;
        } catch (Exception e){
            close();
            System.out.println("Error updating: "+e.getMessage());
            return false;
        }
    }

    public void writeResultSet(ResultSet resultSet) {
        try{
            if (resultSet == null){
                System.out.println("ERROR: NULL RESULT SET!");
                return;
            }
        // ResultSet is initially before the first data set
            //ResultSet tmp = resultSet.
            while (resultSet.next()) {
                System.out.println("-----");
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
                    System.out.println(resultSet.getMetaData().getColumnName(i)+" = "+resultSet.getString(resultSet.getMetaData().getColumnName(i)));   
                }
            }
        } catch (SQLException e){
            System.out.println("Error writing Resultset: "+e.getMessage());
        }
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
              resultSet.close();
            }

            if (statement != null) {
              statement.close();
            }

            if (connect != null) {
              connect.close();
            }
        } catch (Exception e) {
            System.out.println("ERROR: Unable to CLOSE! -"+e.getMessage());
        }
    }
    public static void main(String[] args) throws Exception{
        DatabaseHandler db = new DatabaseHandler();
        String sha1pass = DatabaseHandler.SHAsum("TULIO6011996");
        ResultSet r = db.makeQuerry("select * from accounts where username = 'shiratori' and password = '"+sha1pass+"'");
        db.writeResultSet(r);
    }
}
