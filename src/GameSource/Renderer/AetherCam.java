/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Renderer;

/**
 *
 * @author Shiyang
 */
public class AetherCam {
    private final int length = 850, width = 650;//Note make sure camera dimensions are greater than screen to avoid culling too much
    private int x = 0,y = 0,screenx,screeny;
    public AetherCam(int screenx,int screeny){
        this.screenx = screenx;
        this.screeny = screeny;
    }
    public int[] convertCoords(int x,int y){
        //TODO: Base this translation on screenx and screeny
        return new int[] {x-50,y-50};
    }
    public void updatePosition(int x,int y){
        this.x = x;
        this.y = y;
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
