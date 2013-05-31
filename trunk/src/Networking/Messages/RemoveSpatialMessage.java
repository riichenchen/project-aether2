/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Messages;

/**
 *
 * @author Shiyang
 */
public class RemoveSpatialMessage extends Message{
    private int spatId;
    private String mapId;
    
    public RemoveSpatialMessage(int spatId,String mapId) {
        this.spatId = spatId;
        this.mapId = mapId;
    }

    public String getMapId() {
        return mapId;
    }

    public int getSpatId() {
        return spatId;
    }
    
}
