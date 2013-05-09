/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.TerrainBlocks;
import Spatial.BoundingBox;
import GameSource.Game.GamePoint;
import GameSource.Renderer.RenderSpatial;
import Spatial.Spatial;


/**
 *
 * @author Shiyang
 */
public abstract class AbstractTerrainBlock extends Spatial implements RenderSpatial{
    protected GamePoint location;
    protected BoundingBox dimensions;
    protected boolean solid = true;// is this a solid block? solid by default
    protected String imageType;//block type for rendering
    
    public AbstractTerrainBlock(int x,int y, int z,int dx,int dz,int dy,String imageType,int id){
        super(x,y,z,dx,dz,dy,0,id);
        this.location = new GamePoint(x,y,z);
        this.dimensions = new BoundingBox(dx,dy,dz);
        this.imageType = imageType;
        this.solid = getSolid();
    }
    
    @Override
    public GamePoint getLocation(){
        return location;
    }
    public abstract boolean getSolid();
}
