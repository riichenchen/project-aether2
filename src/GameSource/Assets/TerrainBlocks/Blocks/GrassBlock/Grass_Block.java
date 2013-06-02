/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.TerrainBlocks.Blocks.GrassBlock;

import GameSource.Assets.TerrainBlocks.AbstractTerrainBlock;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class Grass_Block extends AbstractTerrainBlock{

    public Grass_Block(float x,float y,float z){
        super(x,y,z,50,56,30,"grassblock",0);
    }

    @Override
    public boolean getSolid() {
        return true;
    }

    @Override
    public void collideEffect(Spatial s) {
    }
    
}
