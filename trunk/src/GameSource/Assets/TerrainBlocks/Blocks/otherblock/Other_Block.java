/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.TerrainBlocks.Blocks.otherblock;

import GameSource.Assets.TerrainBlocks.Blocks.dirtblock.*;
import GameSource.Assets.AssetManager;
import GameSource.Assets.TerrainBlocks.AbstractTerrainBlock;
import GameSource.Renderer.AetherCam;
import Spatial.BoundingBox;
import GameSource.Renderer.RenderChunk;
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
public class Other_Block extends AbstractTerrainBlock {
    private LinkedList<RenderChunk> renderChunks = new LinkedList<RenderChunk>();
    private Image image = AssetManager.getBlockImage("otherblock");;
    public Other_Block(int x,int y,int z, int id){
        super(x,y,z,1,1,1,"otherblock",id);
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
    public void render(Graphics g, JPanel pane,AetherCam camera) {
        //System.out.println("I render now C:");
        //System.out.println(image+" "+dimensions+" "+camera+" "+pane);
        //System.out.println((int)(location.getX()-camera.getX())+" "+(int)(location.getZ()-camera.getY()));
        //System.out.print(image);
//        g.setColor(Color.red);
//        g.fillRect((int)(location.getX()-camera.getX()), (int)(location.getZ()-camera.getY()), 30,15);
//        g.setColor(Color.black);
//        g.drawRect((int)(location.getX()-camera.getX()), (int)(location.getZ()-camera.getY()), 30,15);
        int[] camSpaceCoords = camera.convertCoords((int)(location.getX()-camera.getX()),(int)(location.getZ()-camera.getY()));
        
        g.drawImage(image, camSpaceCoords[0], camSpaceCoords[1]-20, pane);
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
