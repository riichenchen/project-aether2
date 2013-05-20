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
    public void doAction(Spatial spat) {
        if (actionType == JUMP){
            System.out.println("SPAT JUMPED");
        } else if (actionType == MOVEUP){
            spat.move(0,0,-5);
        } else if (actionType == MOVEDOWN){
            spat.move(0,0,5);
        } else if (actionType == MOVELEFT){
            spat.move(-5,0,0);
        } else if (actionType == MOVERIGHT){
            spat.move(5,0,0);
        }
    }
    
}