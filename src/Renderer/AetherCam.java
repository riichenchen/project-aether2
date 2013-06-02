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
        YLimit = (int)((map.getDimY()-getWidth()/Globals.__PROJECTION_SCALE__)*Globals.__PROJECTION_SCALE__);
    }
    
    public int[] convertCoords(float X,float Y){
        return new int[] {(int)(X-x),(int)(Y-y)};
    }
    
    private void updatePosition(int x,int y){
        this.x = x;
        this.y = y;
    }
    
    public void translateLocation(int x,int y){
        updatePosition(Math.max(Math.min(getX()+x,XLimit),0),Math.max(Math.min(getY()+y,YLimit),0));
    }
    
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
