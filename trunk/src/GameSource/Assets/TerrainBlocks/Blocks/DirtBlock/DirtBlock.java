/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.TerrainBlocks.Blocks.DirtBlock;

import GameSource.Assets.TerrainBlocks.AbstractTerrainBlock;
import Spatial.BoundingBox;
import GameSource.Renderer.RenderChunk;
import Spatial.Spatial;
import java.awt.Graphics;
import java.awt.Panel;
import java.util.LinkedList;

/**
 *
 * @author Shiyang
 */
public class DirtBlock extends AbstractTerrainBlock{
    private LinkedList<RenderChunk> renderChunks = new LinkedList<RenderChunk>();
    public DirtBlock(int x,int y,int z, int id){
        super(x,y,z,1,1,1,"DirtBlock",id);
    }
    
    @Override
    public boolean getSolid() {
        return true;
    }

    @Override
    public void collideEffect(Spatial s) {
        System.out.println("COLLIDED!");
    }

    @Override
    public void render(Graphics g, Panel pane) {
        System.out.println("I render now C:");
    }

    @Override
    public LinkedList<RenderChunk> getChunks() {
        return renderChunks;
    }

    @Override
    public BoundingBox getDimensions() {
        return getBoundingBox();
    }
    
}
