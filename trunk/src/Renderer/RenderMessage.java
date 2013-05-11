/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Renderer;

import Renderer.RenderSpatial;

/**
 *
 * @author Shiyang
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
