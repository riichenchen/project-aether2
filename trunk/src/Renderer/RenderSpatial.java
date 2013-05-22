/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Renderer;

import GameSource.Globals;
import Spatial.Spatial;
import java.awt.Graphics;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 */
public abstract class RenderSpatial extends Spatial{
    protected HashMap<Integer,RenderChunk> renderChunks = new HashMap<>();
    protected Renderer renderer;
    
    public RenderSpatial(float x,float y,float z,float dx,float dz,float dy,float m, float c,int collidable){
        super(x,y,z,dx,dz,dy,m,c,collidable);
    }
    
    public void bindToRenderer(Renderer renderer){
        this.renderer = renderer;
    }
    
    public void unbindFromRenderer(){
        this.renderer = null;
    }
    
    
    /*Override move to update the renderer each time*/
    @Override 
    public void move(float x,float y,float z){
        super.move(x,y,z);
        if (renderer == null){
            if (Globals.RENDER_DEBUG){
                System.out.println("Warning: Spatial "+id+" has no binding to renderer.");
            }
            return;
        }
        renderer.updateSpat(new RenderMessage(this,RenderMessage.UPDATE));
    }
    
    public abstract void render(Graphics g,JPanel pane,AetherCam cam);
    
    public HashMap<Integer,RenderChunk> getRenderChunks(){
        return renderChunks;
    }
    public abstract int[] getCullBounds(float S_QUAD);
}
