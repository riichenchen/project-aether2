/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Renderer;

import Renderer.CameraControls.AbstractCameraControl;
import GameSource.Globals;
import GameSource.game.GameMap;

/**
 *
 * @author Shiyang
 * The aether camera handles all culling regarding what to show in the rendermap.
 * It also provides for translation methods and is controlled by a cameracontrol.
 */
public class AetherCam {
    private int length,width;//Note make sure camera dimensions are greater than screen to avoid culling too much
    private int x = 0,y = 0,XLimit,YLimit;
    private GameMap map;
    private AbstractCameraControl control;
    
    public AetherCam(GameMap map, int length, int width){
        this.length = length;
        this.width = width;
        this.map = map;
        XLimit = map.getDimX()-getLength();
        //We define the y limit by reverse-projecting the game map's actual dimensions onto the screen
        YLimit = (int)((map.getDimY()-getWidth()/Globals.__PROJECTION_SCALE__)*Globals.__PROJECTION_SCALE__);
    }
    
    //Converts the passed in coordinates into camera space
    public int[] convertCoords(float X,float Y){
        return new int[] {(int)(X-x),(int)(Y-y)};
    }
    //More or less a worldTranslation method
    private void updatePosition(int x,int y){
        this.x = x;
        this.y = y;
    }
    //Local translation method, bound by the maps actual dimensions
    public void translateLocation(int x,int y){
        updatePosition(Math.max(Math.min(getX()+x,XLimit),0),Math.max(Math.min(getY()+y,YLimit),0));
    }
    
    //Standard get methods
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getLength(){
        return length;
    }
    public int getWidth(){
        return width;
    }
}
