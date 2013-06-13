/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Renderer;

import Renderer.RenderSpatial;

/**
 *
 * @author Shiyang
 * Standard render message used to communnicate requests from the main game
 * to the renderer
 */
public class RenderMessage {
    public static final int UPDATE = 1,REMOVE = 2;
    private RenderSpatial spatial;
    private int type;
    
    public RenderMessage(RenderSpatial spat,int type){
        spatial = spat;
        this.type = type;
    }
    public RenderSpatial getSpat(){
        return spatial;
    }
    public int getType(){
        return type;
    }
}
