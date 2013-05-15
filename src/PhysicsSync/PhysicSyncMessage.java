/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSync;

import Networking.Messages.Message;
import Spatial.Spatial;
import java.io.Serializable;

/**
 *
 * @author Shiyang
 */
public abstract class PhysicSyncMessage extends Message implements Serializable{
    protected int entityId;
    protected int receivedTime = -1;
    public PhysicSyncMessage(int id){
        this.entityId = id;
    }
    public void setReceiveTime(int time){
        this.receivedTime = time;
    }
    public int getReceivedTime(){
        return receivedTime;
    }
    public abstract void performAction(Spatial spat);
    
    public int getSpatId(){
        return entityId;
    }
}
