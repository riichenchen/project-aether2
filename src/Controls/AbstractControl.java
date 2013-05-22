/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import GameSource.Game.ClientWorldHandler;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public abstract class AbstractControl {
    protected static ClientWorldHandler world;
    private static int controlIds = 0;
    protected int controlId;
    protected Spatial boundTo = null;
    //protected ClientWorldHandler world;
    public AbstractControl(){
        this.controlId = controlIds++;
    }
    public int getControlId(){
        return controlId;
    }
    public void bindToSpatial(Spatial spat){
        this.boundTo = spat;
    }
    
    public static void setWorld(ClientWorldHandler World){
        world = World;
    }
    public ClientWorldHandler getWorld(){
        return world;
    }
    public Spatial getBoundTo(){
        return boundTo;
    }
    public abstract void update();
}
