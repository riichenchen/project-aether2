/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

import GameSource.Globals;
import java.awt.Point;

/**
 *
 * @author Shiyang
 * The Aethermouse is a special spatial that's used to grab a spatial
 * in the space that a mouse click lands on
 */
public class AetherMouse extends Spatial{
    
    public AetherMouse(float x,float z) {
        super(x, 1, z, 100, 430, 0,0,0,0);
    }

    @Override
    public void collideEffect(Spatial s) {
        //shouldn't ever be called
    }
    
    //Called by the GUI if a mouse click landed in-game
    public void click(int mx,int my){
        super.setLocation(mx+boundMap.getCamera().getX(),0,(my+boundMap.getCamera().getY())/Globals.__PROJECTION_SCALE__);
        Spatial[] spats = boundMap.getSpace().grabSpatialsAround(this)[1];
        for (Spatial s: spats){
            if (s instanceof NPC){
                if (((NPC)s).collideMouse())
                    ((NPC)s).clicked();
            }
        }
    }
}
