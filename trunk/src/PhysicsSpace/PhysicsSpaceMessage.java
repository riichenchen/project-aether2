/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSpace;

import Spatial.Spatial;

/**
 *
 * @author Robert
 * 
 * Standard physics space message used to communicate requests from the game
 * to the physics space
 */
public class PhysicsSpaceMessage {
    public final Spatial spat;
    public PhysicsSpaceMessage(Spatial spat){
        this.spat = spat;
    }
}
