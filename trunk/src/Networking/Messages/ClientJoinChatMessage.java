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
public class ClientJoinChatMessage implements Message,Serializable{
    private String name;
    public ClientJoinChatMessage(){}
    public ClientJoinChatMessage(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
