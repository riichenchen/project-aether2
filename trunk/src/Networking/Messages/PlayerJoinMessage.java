/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Messages;

import Database.PlayerData;
import java.io.Serializable;

/**
 *
 * @author Shiyang
 * The playerJoinMessage encapsulates a playerdata
 * class and ships it to the client
 */
public class PlayerJoinMessage extends Message implements Serializable{
    private PlayerData pData;

    public PlayerData getpData() {
        return pData;
    }
    
    public PlayerJoinMessage(PlayerData pData){
        this.pData = pData;
    }
}
