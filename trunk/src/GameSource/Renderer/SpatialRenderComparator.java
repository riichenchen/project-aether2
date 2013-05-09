/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Renderer;

import GameSource.Game.GamePoint;
import java.util.Comparator;

/**
 *
 * @author Shiyang
 */
public class SpatialRenderComparator implements Comparator<RenderSpatial>{
    public static final int LESS = 1,GREATER = -1;
    @Override
    public int compare(RenderSpatial A, RenderSpatial B) {
        GamePoint p1 = A.getLocation();
        GamePoint p2 = B.getLocation();
        if (p1.getY() < p2.getY())
            return GREATER;
        else if (p2.getY() < p1.getY())
            return LESS;
        else
            if (p1.getZ() < p2.getZ())
                return GREATER;
            else if (p2.getZ() < p1.getZ())
                return LESS;
            else 
               if (p1.getX() < p2.getX())
                   return GREATER;
               else if (p2.getX() < p2.getX())
                   return LESS;
        return 0; // the two points are on the exact same position
    }
    
}
