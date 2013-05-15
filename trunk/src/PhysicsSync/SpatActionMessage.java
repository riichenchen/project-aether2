/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSync;

import Spatial.Spatial;
import java.io.Serializable;

/**
 *
 * @author Shiyang
 */
public class SpatActionMessage extends PhysicSyncMessage implements Serializable{
    public static final int NONE = 0,JUMP = 1,MOVELEFT = 2,MOVERIGHT = 3,MOVEUP = 4,MOVEDOWN = 5;
    private int actionType = 0;
    public SpatActionMessage(Spatial spat,int ActionType){
        super(spat.getId());
        this.actionType = ActionType;
    }
    @Override
    public void performAction(Spatial spat) {
        if (actionType == JUMP){
            //spat.jump();
        } else if (actionType == MOVEUP){
            //spat.moveLeft, etc
        }
    }
    
}
