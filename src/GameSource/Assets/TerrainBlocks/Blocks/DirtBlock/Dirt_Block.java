/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.TerrainBlocks.Blocks.DirtBlock;

import GameSource.Assets.TerrainBlocks.AbstractTerrainBlock;
import Spatial.Spatial;

/**
 * A basic dirt block. Fairly self explanatory.
 * @author Shiyang
 */
public class Dirt_Block extends AbstractTerrainBlock {
    public Dirt_Block(float x,float y,float z){
        super(x,y,z,50,56,30,"dirtblock",0);
    }
    
    @Override
    public boolean getSolid() {
        return true;
    }

    @Override
    public void collideEffect(Spatial s) {
        //shouldn't do anything
    }

}
