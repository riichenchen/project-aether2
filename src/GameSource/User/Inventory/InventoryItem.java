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
import Testing.PlayerSpatial;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 * The abstract InventoryItem class is the skeleton by which
 * all inventory items will extend. All items must provide a way for them to
 * be used (despite whether an effect occurs at all or not)
 */
public abstract class InventoryItem extends RenderSpatial{
    private String itemName;
    private Image image;
    private String key;
    
    //the item must have a valid image key as well as an x and y position
    public InventoryItem(String itemId, float x, float z) {
        super(x, 1, z, 40, 40, 40, 0,0,0);
        this.itemName = itemId;
    }
    
    public String getItemId(){
        return itemName;
    }
    
    public void setImage(Image image){
        this.image = image;
    }
    
    public Image getImage(){
        return image;
    }
    
    public abstract void use();
    
    //the render method simply places it on the map standing centered
    // at its location
    @Override
    public void render(Graphics g, JPanel pane, AetherCam camera){
        if (image == null){
            System.out.println("SEVERE: Unable to find image for equip "+itemName);
            System.exit(0);
        }
        int[] camSpaceCoords = camera.convertCoords(location.getX(),location.getZ()*Globals.__PROJECTION_SCALE__);
        g.drawImage(image, camSpaceCoords[0]-(int)length/2, camSpaceCoords[1]-(int)width, pane);
    }
    @Override
    public void collideEffect(Spatial spat){
        if (spat instanceof PlayerSpatial){
            CharacterHandler.addCollideItem(this);
        }
    }
    //standard set and get key methods
    public void setKey(String k){
        this.key = k;
    }
    public String getKey(){
        return key;
    }
}
