/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Messages;

import GameSource.Assets.AssetManager;
import Spatial.MapSpatData;
import Spatial.Spatial;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is a message containing all other entities in the game, and
 * is sent to each new player that joins the game, or whenever a map
 * switch is made. The server loads in all Spatials on that particular map
 * and then sends it to the client that's just joined
 * @author Shiyang
 */
public class MapDataPackageMessage extends Message implements Serializable{
    private MapSpatData[] spatz;
    private String mapId;
    public MapDataPackageMessage(ConcurrentHashMap<Integer,Spatial> spatz,String mapid){
        Spatial[] spats = spatz.values().toArray(new Spatial[0]);
        this.spatz = new MapSpatData[spats.length];
        int counter = 0;
        for (Spatial s: spats){
            this.spatz[counter++] = new MapSpatData(s.getId(),s.getLocation(),AssetManager.SpatialToType(s));
        }
        this.mapId = mapid;
    }
    public MapSpatData[] getSpatData(){
        return spatz;
    }
    public String getMapId(){
        return mapId;
    }
}
