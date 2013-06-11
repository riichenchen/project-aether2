/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.TerrainBlocks;
import GameSource.Assets.AssetManager;
import GameSource.Game.GamePoint;
import GameSource.Globals;
import Renderer.AetherCam;
import Renderer.RenderChunk;
import Renderer.RenderSpatial;
import Spatial.Spatial;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import javax.swing.JPanel;


/**
 * All terrain blocks must extend this template.
 * Terrain blocks must provide an image
 * @author Shiyang
 */
public abstract class AbstractTerrainBlock extends RenderSpatial{
    
    protected boolean solid = true;// is this a solid block? solid by default
    protected Image image;
    
    public AbstractTerrainBlock(float x,float y, float z,int dx,int dz,int dy,String imageType,int collidable){
        super(x,y,z,dx,dz,dy,0,0,collidable);
        this.image = AssetManager.getBlockImage(imageType);
        this.solid = getSolid();
    }
    
    //simply blit the picture on the screen.
    @Override
    public void render(Graphics g, JPanel pane,AetherCam camera) {//rendering time C:
        int[] camSpaceCoords = camera.convertCoords(location.getX(),location.getZ()*Globals.__PROJECTION_SCALE__);
        
        g.drawImage(image, camSpaceCoords[0]-(int)length/2, camSpaceCoords[1]-(int)width, pane);
    }
    
    public abstract boolean getSolid();
}
