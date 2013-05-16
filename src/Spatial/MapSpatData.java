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
    public MapSpatData(int id,GamePoint loc){
        this.id = id;
        this.loc = loc;
    }
    public int getId(){
        return id;
    }
    public GamePoint getLocation(){
        return loc;
    }
}
