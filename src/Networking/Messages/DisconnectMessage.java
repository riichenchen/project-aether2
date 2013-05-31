/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Messages;

import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class DisconnectMessage extends Message{
    private int spatId;
    private String mapId;
    public DisconnectMessage(Spatial spat) {
        this.spatId = spat.getId();
        this.mapId = spat.getMap().getName();
    }

    public int getSpatId() {
        return spatId;
    }

    public String getMapId() {
        return mapId;
    }
    
}
