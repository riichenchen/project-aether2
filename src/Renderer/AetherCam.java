/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Renderer;

/**
 *
 * @author Shiyang
 */
public class AetherCam {
    private int length,width;//Note make sure camera dimensions are greater than screen to avoid culling too much
    private int x = 0,y = 0;
    public AetherCam(int length,int width){
        this.length = length;
        this.width = width;
    }
    public int[] convertCoords(int x,int y){
        //TODO: Base this translation on screenx and screeny
        return new int[] {x,y};
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