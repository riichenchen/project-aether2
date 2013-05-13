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
//    public abstract GamePoint getLocation();
    protected HashMap<Integer,RenderChunk> renderChunks = new HashMap<>();
    protected Renderer renderer;
    
<<<<<<< .mine
    public RenderSpatial(float x,float y,float z,float dx,float dz,float dy,float m){
        super(x,y,z,dx,dz,dy,m);
=======
    public RenderSpatial(float x,float y,float z,float dx,float dz,float dy,float m, float c,int id){
        super(x,y,z,dx,dz,dy,m,c,id);
>>>>>>> .r28
    }
    
    public void bindToRenderer(Renderer renderer){
        this.renderer = renderer;
    }
    
    public void unbindFromRenderer(){
        this.renderer = null;
    }
    
    
    /*Override move to update the renderer each time*/
    @Override 
    public void move(int x,int y,int z){
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
 //   public abstract int getId();
    
    public HashMap<Integer,RenderChunk> getChunks(){
        return renderChunks;
    }
}
