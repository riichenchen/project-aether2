/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Messages;

import java.io.Serializable;

/**
 *
 * @author Shiyang
 * The Skeleton message class.
 * All messages must extend this class for serialization as well
 * as server/client synchronization
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
