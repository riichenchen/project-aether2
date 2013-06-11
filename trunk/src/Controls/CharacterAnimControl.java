/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import Animation.AnimTrack;
import Animation.SpriteSet;
import GameSource.Globals;
import Renderer.AetherCam;
import Spatial.Spatial;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

/**
 * The CharacterAnimControl class is the medium by which
 * an animated spatial can be seen in the map. It requires
 * a sprite set upon initialization and will use that spriteset
 * to render onto the screen.
 * @author Shiyang
 */
public class CharacterAnimControl extends AbstractControl{
    private int time;
    private int limit; //how much time before the track animation wraps
    private String currentTrack;
    private SpriteSet spriteSet;
    private Random myRandom;
    
    public CharacterAnimControl(SpriteSet spriteSet){
        this.time = 0;
        this.currentTrack = spriteSet.getDefaultTrack();//just so the thing doesn't die on us
        this.limit = spriteSet.get(currentTrack).getLimit();
        this.spriteSet = spriteSet;
        myRandom = new Random();
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
    public void update() {//the only update required really is increasing time
        time = (time+1)%limit;
    }

    //The swapAnim method switches the current track being played
    //to a new track using a track name string.
    public void swapAnim(String trackName){
        if (!currentTrack.equals(trackName)){
            time = 0;//If this is a new track, start at time = 0
            limit = spriteSet.get(trackName).getLimit();//update the limit
        }
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
        int[] camSpaceCoords = camera.convertCoords(boundTo.getX(),boundTo.getZ()*Globals.__PROJECTION_SCALE__);
        g.drawImage(theTrack.getAt(time),camSpaceCoords[0]-theTrack.getOffX(),camSpaceCoords[1]-theTrack.getOffY(),pane);
    }
    
    //Returns the loop time of this animation
    public int getLimit() {
        return limit;
    }
    
    //Returns a random animation (used by things like npc that pick random animations)
    public void swapRandomAnim(){
        swapAnim(spriteSet.getKey(myRandom.nextInt(spriteSet.getNumTracks())));
    }
    
    public int getImageWidth(){
        return spriteSet.get(currentTrack).getImageWidth(time);
    }
    public int getImageHeight(){
        return spriteSet.get(currentTrack).getImageHeight(time);
    }
    public int getOffX(){
        return spriteSet.get(currentTrack).getOffX();
    }
    public int getOffY(){
        return spriteSet.get(currentTrack).getOffY();
    }
}
