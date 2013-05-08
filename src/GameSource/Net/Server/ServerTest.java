/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Server;

import Database.DatabaseHandler;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Shiyang
 */
public class ServerTest {
    private DatabaseHandler db;
    public ServerTest(){
        db = new DatabaseHandler();
        MyServer myserver = new MyServer(this);
        myserver.start();
    }
    
    public static void main(String[] args){
//        MyServer myserver = new MyServer(this);
//        myserver.start();
        new ServerTest();
    }
    
    public boolean RequestLogin(String username, String password){
        //System.out.println("okay to querry");
        ResultSet r = db.makeQuerry("Select * from accounts where username = '"+username+"' and password = '"+password+"'");
        try {
            if (r.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e){
            System.out.println("ERROR LOGGING IN! "+e.getMessage());
            return false;
        }
    }
}
