/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

import GameSource.Game.GamePoint;
import java.io.Serializable;

/**
 *
 * @author Shiyang
 */
public class MapSpatData implements Serializable {
    private int id;
    private GamePoint loc;
    private int type;
    public MapSpatData(int id,GamePoint loc,int type){
        this.id = id;
        this.loc = loc;
        this.type = type;
    }
    public int getId(){
        return id;
    }
    public GamePoint getLocation(){
        return loc;
    }
    public int getType(){
        return type;
    }
}
