/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Messages;

import java.io.Serializable;

/**
 *
 * @author Shiyang
 * The registerclient message is used by the server to communicate to a client
 * it's registered client id code so it can recognize server messages from
 * forwarded ones
 * with 
 */
public class RegisterClientMessage extends Message implements Serializable {
    public RegisterClientMessage(){}
    private int ClientID;
    public RegisterClientMessage(int id){
        this.ClientID = id;
    }
    public int getClientId(){
        return ClientID;
    }
}
