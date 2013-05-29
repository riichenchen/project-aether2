/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSpace;

import Math.Vector2D;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class PMoveSpatialMessage extends PhysicsSpaceMessage{
    private Vector2D motion;
    public PMoveSpatialMessage(Spatial spat,Vector2D motion) {
        super(spat);
        this.motion = motion;
    }

    public Vector2D getMotion() {
        return motion;
    }
    
}
