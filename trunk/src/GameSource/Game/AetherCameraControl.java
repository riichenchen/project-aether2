/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Game;

import GameSource.Globals;
import Renderer.CameraControls.AbstractCameraControl;

/**
 * The camera control of the game. It limits the cameras movement to within the map
 * by using max and min.
 * 
 * Note: Globals.__PROJECTION_SCALE__ converts the camera's width to it's actual value
 * when projected into game space
 * @author Shiyang
 */
public class AetherCameraControl extends AbstractCameraControl{

    @Override
    public void Update() {
        camera.translateLocation((int)(boundSpat.getX()-camera.getX()-camera.getLength()/2),(int)(boundSpat.getZ()*Globals.__PROJECTION_SCALE__-camera.getY()-camera.getWidth()/2));
    }
    
}
