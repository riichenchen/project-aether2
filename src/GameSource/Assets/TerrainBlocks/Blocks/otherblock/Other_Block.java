/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.TerrainBlocks.Blocks.otherblock;

import GameSource.Assets.AssetManager;
import GameSource.Assets.TerrainBlocks.AbstractTerrainBlock;
import Renderer.AetherCam;
import Renderer.RenderChunk;
import Spatial.BoundingBox;
import Spatial.Spatial;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 */
public class Other_Block extends AbstractTerrainBlock {
    public Other_Block(int x,int y,int z, int id){
        super(x,y,z,45,113,1,"otherblock",id);
    }
    
    @Override
    public boolean getSolid() {
        return true;
    }

    @Override
    public void collideEffect(Spatial s) {
        System.out.println("COLLIDED!");
    }

}
