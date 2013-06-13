/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSpace;

import Spatial.Spatial;

/**
 *
 * @author Shiyang
 * An update spatial message that tells the physics space which spatial
 * to update.
 */
public class PUpdateSpatialMessage extends PhysicsSpaceMessage{
    public PUpdateSpatialMessage(Spatial spat){
        super(spat);
    }
}
