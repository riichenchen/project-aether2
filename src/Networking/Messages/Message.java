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
public abstract class Message implements Serializable{
    private int ClientId = -1;//Default: Server message
    public Message(){
    }
    public void setClientId(int clientid){
        this.ClientId = clientid;
    }
    public int getClientId(){
        return ClientId;
    }
}
