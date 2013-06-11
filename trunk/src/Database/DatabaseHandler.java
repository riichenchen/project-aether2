package Database;

import GameSource.Globals;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * The DatabaseHandler class handles all ingoing and outbound
 * connections and queries to the database.
 * It provides a query and update method for sql execution.
 * @author Shiyang
 */
public class DatabaseHandler {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public DatabaseHandler(){
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                .getConnection("jdbc:mysql://"+Globals.DBIP+"/projectaether2?"
                    + "user="+Globals.__SQLUSER__+"&password="+Globals.__SQLPASS__);
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
    //Since the hashing returns the sha-code in a byte array, we must
    //convert back to a String
    private static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
    //Basic querry method. Takes in a string and executes it in sql
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
    //Basic update method. Takes in a string and executes it in sql
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

    // A close method to kill the connection after each session
    // Note: may not be used depending on future implementations
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
}
