/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Messages;

import java.io.Serializable;

/**
 *
 * @author Shiyang
 */
public class ClientJoinMessage extends Message implements Serializable{
    private String clientName;
    public ClientJoinMessage(){}
    public ClientJoinMessage(String name){
        clientName = name;
    }
    public String getName(){
        return clientName;
    }
}