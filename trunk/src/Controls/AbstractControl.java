/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import GameSource.Game.ClientWorldHandler;
import Spatial.Spatial;

/**
 * All controls in this game (what makes ai move, what animates spatials,
 * what controls behavior) must extend this class. The AbstractControl
 * class provides many basic methods such as bindToSpatial. All child classes
 * must implement an update method (or what this control does).
 * 
 * Note: All Controls have access to the ClientWorldHandler incase modifications
 * need to be made the world itself.
 * @author Shiyang
 */
public abstract class AbstractControl {
    protected static ClientWorldHandler world;
    private static int controlIds = 0;
    protected int controlId;
    protected Spatial boundTo = null;

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
    
    //Must be implemented by a control
    public abstract void update();
}
