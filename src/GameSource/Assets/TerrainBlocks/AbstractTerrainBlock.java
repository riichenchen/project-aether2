/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.TerrainBlocks;
import GameSource.Assets.AssetManager;
import Renderer.AetherCam;
import Renderer.RenderChunk;
import Renderer.RenderSpatial;
import Spatial.Spatial;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import javax.swing.JPanel;


/**
 *
 * @author Shiyang
 */
public abstract class AbstractTerrainBlock extends RenderSpatial{
    
    protected boolean solid = true;// is this a solid block? solid by default
    //protected String imageType;//block type for rendering
    protected Image image;
    
    /*TODO:
     - Change block image to an image set
     - one for the top view and one for the front profile of the block*/
    
    public AbstractTerrainBlock(float x,float y, float z,int dx,int dz,int dy,String imageType,int collidable){
        super(x,y,z,dx,dz,dy,0,0,collidable);
        this.image = AssetManager.getBlockImage(imageType);
        this.solid = getSolid();
    }
    
    @Override
    public void render(Graphics g, JPanel pane,AetherCam camera) {//rendering time C:
        //TODO: Add perspective to this thing
        int[] camSpaceCoords = camera.convertCoords(location.getX(),location.getZ());
        
        g.drawImage(image, camSpaceCoords[0], camSpaceCoords[1]-((int)location.getY()*2), pane);
    }
    
    public abstract boolean getSolid();
    
}
