/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Renderer;

import Spatial.Spatial;
import java.awt.Graphics;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 */
public abstract class RenderSpatial extends Spatial{
//    public abstract GamePoint getLocation();
    protected Renderer renderer;
    
    public RenderSpatial(float x,float y,float z,float dx,float dz,float dy,float m, float c,int id){
        super(x,y,z,dx,dz,dy,m,c,id);
    }
    
    public void bindToRenderer(Renderer renderer){
        this.renderer = renderer;
    }
    
    /*Override move to update the renderer each time*/
    @Override 
    public void move(int x,int y,int z){
        super.move(x,y,z);
        renderer.updateSpat(new RenderMessage(this,RenderMessage.UPDATE));
    }
    
    public abstract void render(Graphics g,JPanel pane,AetherCam cam);
 //   public abstract int getId();
    public abstract LinkedList<RenderChunk> getChunks();
}
