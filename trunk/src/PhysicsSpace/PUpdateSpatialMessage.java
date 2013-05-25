/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSpace;

import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class PUpdateSpatialMessage extends PhysicsSpaceMessage{
    public PUpdateSpatialMessage(Spatial spat){
        super(spat);
    }
}
