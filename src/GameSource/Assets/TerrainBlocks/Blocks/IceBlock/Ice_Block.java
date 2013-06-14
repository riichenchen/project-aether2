/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.TerrainBlocks.Blocks.IceBlock;

import GameSource.Assets.TerrainBlocks.AbstractTerrainBlock;
import Spatial.Spatial;

/**
 * Basic ice block. Fairly self explanatory
 * @author Shiyang
 */
public class Ice_Block extends AbstractTerrainBlock{

    public Ice_Block(float x,float y,float z){
        super(x,y,z,50,56,30,"iceblock",0);
    }

    @Override
    public boolean getSolid() {
        return true;
    }

    @Override
    public void collideEffect(Spatial s) {
    }
    
}
