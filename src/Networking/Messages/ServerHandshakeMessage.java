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
public class ServerHandshakeMessage implements Serializable,Message{
    public ServerHandshakeMessage(){}
    float sVersion;
    public ServerHandshakeMessage(float serverVersion){
        sVersion = serverVersion;
    }
    public float getVersion(){
        return sVersion;
    }
}
