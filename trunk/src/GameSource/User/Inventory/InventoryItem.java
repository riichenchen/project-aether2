/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User.Inventory;

import GameSource.Globals;
import GameSource.User.CharacterHandler;
import Renderer.AetherCam;
import Renderer.RenderSpatial;
import Spatial.Spatial;
import Testing.Stevey;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 */
public abstract class InventoryItem extends RenderSpatial{
    private String itemId;
    private Image image;
    
    public InventoryItem(String itemId, float x, float z) {
        super(x, 1, z, 40, 40, 40, 0,0,0);
        this.itemId = itemId;
    }
    
    public String getItemId(){
        return itemId;
    }
    
    public void setImage(Image image){
        this.image = image;
    }
    public abstract void use();
    
    @Override
    public void render(Graphics g, JPanel pane, AetherCam camera){
        if (image == null){
            System.out.println("SEVERE: Unable to find image for equip "+itemId);
            System.exit(0);
        }
        int[] camSpaceCoords = camera.convertCoords(location.getX(),location.getZ()*Globals.__PROJECTION_SCALE__);
        g.drawImage(image, camSpaceCoords[0]-(int)length/2, camSpaceCoords[1]-(int)width, pane);
    }
    @Override
    public void collideEffect(Spatial spat){
        if (spat instanceof Stevey){
            CharacterHandler.addCollideItem(this);
        }
    }
}
