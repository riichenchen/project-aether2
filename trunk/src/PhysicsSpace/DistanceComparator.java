/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSpace;

import GameSource.User.CharacterHandler;
import Spatial.Spatial;
import java.util.Comparator;

/**
 *
 * @author Shiyang
 * An implementation comparator that prioritizes objects that are closer
 * to the player.
 */
public class DistanceComparator implements Comparator<Spatial>{
    public static final int LESS = 1,GREATER = -1;
    @Override
    public int compare(Spatial o1, Spatial o2) {
        float d1 = o1.distSquared(CharacterHandler.getPlayer());
        float d2 = o2.distSquared(CharacterHandler.getPlayer());
        if (d1 < d2)
            return GREATER;
        else if (d1 > d2)
            return LESS;
        else
            return 0;
    }
    
}
