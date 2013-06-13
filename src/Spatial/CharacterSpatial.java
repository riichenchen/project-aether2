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
import java.awt.Point;
import java.awt.Polygon;
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
        addControl(getAnimControl());
    }
    
    @Override 
    public void render(Graphics g,JPanel pane, AetherCam cam){
        //some debug to show the bounding box and other properties if the physics debug is set
        //to level 4 or lower
        if (Globals.__PHYSICSDEBUG__ <5){
            Polygon theShape = getShape();
            int[] allX = theShape.xpoints;
            int[] allY = theShape.ypoints;
            for (int i = 0; i < 4; i++){ // parse through the objects bounding rect points, converting to cam space
                int[] locs = cam.convertCoords(allX[i], allY[i]*Globals.__PROJECTION_SCALE__);
                allX[i] = locs[0];
                allY[i] = locs[1];
            }
            g.setColor(Color.blue);
            g.drawPolygon(new Polygon(allX,allY,4));
            g.setColor(Color.green);
            g.drawLine(allX[2],allY[2],allX[3],allY[3]);
            g.setColor(Color.PINK);
            g.drawLine(allX[0],allY[0],allX[3],allY[3]);
            g.drawLine(allX[2],allY[2],allX[1],allY[1]);
            g.setColor(Color.red);
            int[] loc = cam.convertCoords(this.getX()-this.getLength()/2, this.getZ()*Globals.__PROJECTION_SCALE__ -this.getHeight());
            g.drawRect(loc[0],loc[1],(int)this.getLength(),(int)this.getHeight());
        }
        
        Object charAnimControl = this.getControl(CharacterAnimControl.class);
        
        //Should always have a charanimcontrol, but incase something happens...
        if (charAnimControl != null){
            ((CharacterAnimControl)charAnimControl).render(g, pane, cam);
        } else {
            System.out.println("Warning: No Character Anim Track for spatial "+this.getId());
        }
    }
    //All implementations should provide a characteranimcontrol object so this spat can actually be animated
    public abstract CharacterAnimControl getAnimControl();
}
