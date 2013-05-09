/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Renderer;

import GameSource.Game.GamePoint;
import GameSource.game.GameMap;
import Spatial.BoundingBox;
import java.awt.Graphics;
import java.awt.Panel;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 *
 * @author Shiyang
 */
public class Renderer {
    private Graphics g;
    private Panel pane;
    private Comparator<RenderSpatial> comparator = new SpatialRenderComparator();
    private PriorityQueue<RenderSpatial> renderObjects = new PriorityQueue<>(10,comparator);
    private RenderChunk[][] renderMap;
    private LinkedList<RenderMessage> updateSpatMessages = new LinkedList<RenderMessage>();
    private AetherCam camera = null;
    
    private GameMap map;
    
    public Renderer(Graphics g,Panel pane,GameMap map){
        this.g = g;
        this.pane = pane;
        this.map = map;
        this.renderMap = new RenderChunk[(int)(600*0.8)][(int)(800*0.8)];
    }
    public void render(){
        if (camera == null){
            System.out.println("UNABLE TO RENDER WITHOUT CAMERA!");
        }
        //todo: draw out all objects in the render list;
        //g.drawRect(x, y, width, height)
    }
    public void updateSpat(RenderMessage message){
        updateSpatMessages.add(message);
    }
    public void update(){
        while (updateSpatMessages.peek() != null){
            RenderMessage mymsg = updateSpatMessages.poll();
            if (mymsg.getType()== RenderMessage.UPDATE){
                RenderSpatial myspat = mymsg.getSpat();
                //Take out the spatial from any chunks that may still point to it
                while (myspat.getChunks().peek()!= null){
                    RenderChunk myChunk = myspat.getChunks().poll();
                    myChunk.removeObject(myspat);
                }
                add(myspat);
            }
        }
    }
    public void add(RenderSpatial spat){
        GamePoint location = spat.getLocation();
        float x = location.getX();
        float y = location.getZ();
        BoundingBox dims = spat.getDimensions();
        int sizex = (int)Math.floor(dims.getLength()*0.8);
        int sizey = (int)Math.ceil(dims.getWidth()*0.8);
        //for (int i = )
    }
}
