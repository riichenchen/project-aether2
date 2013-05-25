/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Effects;

import Effects.AbstractEffect;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class IceyEffect extends AbstractEffect{

    public IceyEffect(float x, float y, float z) {
        super(x, y, z, 181, 102, 50, "icysprite");
    }

    @Override
    public void collideEffect(Spatial s) {
        
    }
    
}
