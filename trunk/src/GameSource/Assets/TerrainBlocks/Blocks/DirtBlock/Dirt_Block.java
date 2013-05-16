/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.TerrainBlocks.Blocks.DirtBlock;

import GameSource.Assets.AssetManager;
import GameSource.Assets.TerrainBlocks.AbstractTerrainBlock;
import Renderer.AetherCam;
import Spatial.BoundingBox;
import Renderer.RenderChunk;
import Spatial.Spatial;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
//import java.awt.Image;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 */
public class Dirt_Block extends AbstractTerrainBlock {
    public Dirt_Block(float x,float y,float z){
        super(x,y,z,50,28,1,"dirtblock",0);
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
