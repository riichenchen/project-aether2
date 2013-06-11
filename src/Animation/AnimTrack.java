/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Animation;

import java.awt.Image;
import java.util.ArrayList;

/**
 * The animation track class keeps track of a single animation inside a spriteset.
 * It tracks the order of display for frames and also supports repeating frames
 * without copying the frame's image resource. It also tracks the offset x and y
 * of the specific image.
 * @author Shiyang
 */
public class AnimTrack {
    private Image[] images;
    
    private ArrayList<Integer> frameOrder;
    //Note: for frameOrder, may consider in the future to implement a not-so
    //size-costly method of tracking down frames vs time
    private int imageCounter,offX,offY;
    
    public AnimTrack(int n,int offX,int offY){
        this.images = new Image[n];
        this.frameOrder = new ArrayList<Integer>();
        this.imageCounter = 0; // used for loading in individual images
        this.offX = offX;
        this.offY = offY;
    }
    
    public void addFrame(int n,int length){
        //Note: access time for each frame is rather constant
        //but in consequence, an additional run-cost due to resizing
        //occurs when initializing the AnimTrack
        for (int i = 0; i < length/10; i++){
            frameOrder.add(n);
        }
    }
    
    public void addImage(Image img){
        if (imageCounter == images.length){ // should never occur, but just incase,
            System.out.println("WARNING: Trying to add more images to AnimTrack than specified!");
            return;
        }
        images[imageCounter++] = img;
    }
    
    public int getLimit(){ // The number of frames in this track
        return frameOrder.size();
    }
    public Image getAt(int time){ // returns the image at a specific time t
        return images[frameOrder.get(time)];
    }
    
    //off x and y of the image (often the center of the image)
    public int getOffX(){
        return offX;
    }
    public int getOffY(){
        return offY;
    }
    //Returns the width and height of the image
    public int getImageWidth(int x){
        return images[frameOrder.get(x)].getWidth(null);
    }
    public int getImageHeight(int x){
        return images[frameOrder.get(x)].getHeight(null);
    }
}
