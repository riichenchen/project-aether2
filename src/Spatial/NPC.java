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
import java.awt.Polygon;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 */
public class NPC extends RenderSpatial{
    private String name;
    private int time = 0;
    private CharacterAnimControl animControl;
    
    public NPC(float x, float y, float z,String name,CharacterAnimControl animControl){
        super(x,y,z,0,0,0,0,0,0);
        addControl(animControl);
        this.animControl = animControl;
        length = animControl.getImageWidth();
        height = animControl.getImageHeight();
        this.name = name;
    }
    
    @Override 
    public void render(Graphics g,JPanel pane, AetherCam cam){
        if (Globals.__PHYSICSDEBUG__ <5){
            Polygon theShape = getShape();
            int[] allX = theShape.xpoints;
            int[] allY = theShape.ypoints;
            for (int i = 0; i < 4; i++){
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
            //            g.drawRect(loc[0], loc[1], (int)this.getLength(),(int)this.getWidth());
            g.setColor(Color.red);
            int[] loc = cam.convertCoords(this.getX()-this.getLength()/2, this.getZ()*Globals.__PROJECTION_SCALE__ -this.getHeight());
            g.drawRect(loc[0],loc[1],(int)this.getLength(),(int)this.getHeight());
            g.setColor(Color.white);
            
            //show sprite width (cause that's what we realy care about)
            theShape = getSpriteBox();
            allX = theShape.xpoints;
            allY = theShape.ypoints;
            for (int i = 0; i < 4; i++){ // convert into camera space
                int[] locs = cam.convertCoords(allX[i], allY[i]);
                allX[i] = locs[0];
                allY[i] = locs[1];
            }
            g.drawPolygon(new Polygon(allX,allY,4));
        }
        Object charAnimControl = this.getControl(CharacterAnimControl.class);
        if (charAnimControl != null){
            ((CharacterAnimControl)charAnimControl).render(g, pane, cam);
        } else {
            System.out.println("Warning: No Character Anim Track for spatial "+this.getId());
        }
    }

    @Override
    public void collideEffect(Spatial s) {
        //nothing occurs (this shouldn't every be called for that matter
    }
    @Override
    public void update(){
        super.update();
        time++;
        if (time == animControl.getLimit()){
           animControl.swapRandomAnim();
           length = animControl.getImageWidth();
           height = animControl.getImageHeight();
           time = 0;
        }
    }
    int n = 0;
    public void clicked(){
        System.out.println(name+" has been clicked! "+(n++));
    }
    
    public boolean collideMouse(){
        return getSpriteBox().contains(Globals.theMouse.getX(),Globals.theMouse.getZ()*Globals.__PROJECTION_SCALE__);
    }
    public Polygon getSpriteBox(){
        int offx = animControl.getOffX();
        int offy = animControl.getOffY();
        int[] allX = new int[]{(int)(getX()-offx),(int)(getX()-offx),(int)(getX()-offx+getLength()),(int)(getX()-offx+getLength())};
        double cZ = getZ()*Globals.__PROJECTION_SCALE__;
        int[] allY = new int[]{(int)(cZ-offy+getHeight()),(int)(cZ-offy),(int)(cZ-offy),(int)(cZ-offy+getHeight())};
        return new Polygon(allX,allY,4);
    }
}
