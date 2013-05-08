/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Messages;

import Database.DatabaseHandler;
import java.io.Serializable;

/**
 *
 * @author Shiyang
 */
public class RequestLoginMessage extends Message implements Serializable{
    public RequestLoginMessage(){}
    private String user,pass;
    public RequestLoginMessage(String user, String pass){
        this.user = user;
        this.pass = DatabaseHandler.SHAsum(pass);
    }
    public String getUser(){
        return user;
    }
    public String getPass(){
        return pass;
    }
}
