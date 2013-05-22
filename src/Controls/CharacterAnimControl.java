/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import Animation.AnimTrack;
import Animation.SpriteSet;
import Renderer.AetherCam;
import Spatial.Spatial;
import java.awt.Graphics;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 */
public class CharacterAnimControl extends AbstractControl{
    private int time;
    private int limit;
    private String lastTrack;
    private String currentTrack;
    private SpriteSet spriteSet;
    
    public CharacterAnimControl(SpriteSet spriteSet){
        this.time = 0;
//        this.lastTrack = "";
        this.currentTrack = spriteSet.getDefaultTrack();//just so the thing doesn't die on us
        this.limit = spriteSet.get(currentTrack).getLimit();
        this.spriteSet = spriteSet;
    }
    
    @Override
    public void bindToSpatial(Spatial spat){
        //Spatial should only have 1 instance of CharacterAnimControl at any given time
        if (spat.getControl(CharacterAnimControl.class) != null){
            spat.removeControl(CharacterAnimControl.class);
        }
        super.bindToSpatial(spat);
    }
    
    @Override
    public void update() {
        time = (time+1)%limit;
    }

    public void swapAnim(String trackName){
        if (!currentTrack.equals(trackName)){
            time = 0;
            limit = spriteSet.get(trackName).getLimit();
        }
//        lastTrack = trackName;
        currentTrack = trackName;
    }
    
    //Note: This render isn't directly called by the renderer, rather it is
    //used as an add on to a spatial's render.
    public void render(Graphics g, JPanel pane,AetherCam camera){
        AnimTrack theTrack = spriteSet.get(currentTrack);
        if (theTrack == null){
            System.out.println("Warning: track name "+currentTrack+" couldn't be found!");
            return;
        }
        //Note: recall that we're mapping 2D onto 2.5 d, so the 2.5d z axis is the 2d y axis
        int[] camSpaceCoords = camera.convertCoords(boundTo.getX(),boundTo.getZ());
        g.drawImage(theTrack.getAt(time),camSpaceCoords[0]-theTrack.getOffX(),camSpaceCoords[1]-theTrack.getOffY(),pane);
    }
    
}