/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import Spatial.Spatial;
import Testing.GamePanel;

/**
 *
 * @author Shiyang
 */
public abstract class AbstractControl {
    private static int controlIds = 0;
    protected int controlId;
    protected Spatial boundTo = null;
    protected GamePanel world;
    public AbstractControl(){
        this.controlId = controlIds++;
    }
    public int getControlId(){
        return controlId;
    }
    public void bindToSpatial(Spatial spat){
        this.boundTo = spat;
        //TODO: bind to world for access to world events
        //this.world = world;
    }
    
    public abstract void update();
}
