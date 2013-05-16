/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Messages;

import GameSource.Game.GamePoint;
import java.io.Serializable;

/**
 *
 * @author Shiyang
 */
public class PlayerJoinMessage extends Message implements Serializable{
    private int characterType;
    private GamePoint location;
    private String mapid;
    private int spatId;
    private int clientId;
    
    public PlayerJoinMessage(int characterType,GamePoint location,String mapid,int spatId,int clientId){
        this.characterType = characterType;
        this.location = location;
        this.mapid = mapid;
        this.spatId = spatId;
        this.clientId = clientId;
    }
    public int getSpatId(){
        return spatId;
    }
    public int getCharacterType(){
        return characterType;
    }
    public GamePoint getLocation(){
        return location;
    }
    public String getMapId(){
        return mapid;
    }
    public int getClientId(){
        return clientId;
    }
}
