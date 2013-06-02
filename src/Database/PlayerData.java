/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import GameSource.Game.GamePoint;
import java.io.Serializable;

/**
 *
 * @author Shiyang
 */
public class PlayerData implements Serializable{
    private int characterType;
    private GamePoint location;
    private String mapType;
    private int accountId;
    
    public int getCharacterType() {
        return characterType;
    }

    public GamePoint getLocation() {
        return location;
    }

    public String getMapId() {
        return mapType;
    }
    
    public int getAccountId() {
        return accountId;
    }
    
    public PlayerData(int accountId,int CharacterType,GamePoint location,String mapType){
        this.characterType = CharacterType;
        this.location = location;
        this.mapType = mapType;
        this.accountId = accountId;
    }
}
