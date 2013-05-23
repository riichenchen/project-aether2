/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

import Controls.CharacterAnimControl;
import GameSource.Globals;
import Renderer.AetherCam;
import Renderer.RenderSpatial;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Special instance of spatial where a state and other specific management
 * is needed regarding a character in game, such as a monster.
 * @author Shiyang
 */

public abstract class CharacterSpatial extends RenderSpatial{
    
//    protected int state;
    public CharacterSpatial(float x, float y, float z, float l, float h, float w, float m, float c, int collidable){
        super(x,y,z,l,h,w,m,c,collidable);
//        this.state = 0; // Default state for stand
    }
    //JUMPING = 1,MOVINGLEFT = 2,MOVINGRIGHT = 3,MOVINGUP = 4,MOVINGDOWN = 5
    //potentially add more later... (depends on implementation)
//    public void setState(int state){
//        this.state = state;
//    }
    
    @Override 
    public void render(Graphics g,JPanel pane, AetherCam cam){
        if (Globals.__PHYSICSDEBUG__){
            g.setColor(Color.blue);
            int[] loc = cam.convertCoords(this.getX()-this.getLength()/2, this.getZ()-this.getWidth());
            g.drawRect(loc[0], loc[1], (int)this.getLength(),(int)this.getWidth());
            g.setColor(Color.red);
            loc = cam.convertCoords(this.getX()-this.getLength()/2, this.getZ()-this.getHeight());
            g.drawRect(loc[0],loc[1],(int)this.getLength(),(int)this.getHeight());
        }
        Object charAnimControl = this.getControl(CharacterAnimControl.class);
        if (charAnimControl != null){
            ((CharacterAnimControl)charAnimControl).render(g, pane, cam);
        } else {
            System.out.println("Warning: No Character Anim Track for spatial "+this.getId());
        }
    }
    
    @Override
    public int[] getCullBounds(float S_QUAD){
        int x = (int)Math.floor((getX()-getLength()/2)*S_QUAD);
        int y = (int)Math.floor((getZ()-getHeight())*S_QUAD);
        int sizex = (int)Math.ceil(getLength()*S_QUAD);
        int sizey = (int)Math.ceil(getHeight()*S_QUAD);
        return new int[]{x,y,sizex,sizey};
    }
}
