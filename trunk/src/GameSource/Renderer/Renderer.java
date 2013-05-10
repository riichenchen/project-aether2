/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Renderer;

import GameSource.Game.GamePoint;
import GameSource.game.GameMap;
import Spatial.BoundingBox;
import java.awt.Graphics;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 */
public class Renderer {
    private Comparator<RenderSpatial> comparator = new SpatialRenderComparator();
    private PriorityQueue<RenderSpatial> renderObjects = new PriorityQueue<>(10,comparator);
    private RenderChunk[][] renderMap;
    private LinkedList<RenderMessage> updateSpatMessages = new LinkedList<RenderMessage>();
    private AetherCam camera = null;
    
    //Constants represent a scale factor by which to divide the entire "map" into
    //and organize spatials by
    private final float S_QUAD = 0.05f;
    
    private GameMap map;
    
    public Renderer(GameMap map){
        this.map = map;
        this.renderMap = new RenderChunk[(int)(map.getDimY()*S_QUAD)][(int)(map.getDimX()*S_QUAD)];
        for (int i = 0; i < (int)(map.getDimY()*S_QUAD);i++){
            for (int j = 0; j < (int)(map.getDimX()*S_QUAD);j++){
                renderMap[i][j] = new RenderChunk();
            }
        }
    }
    
    public void render(Graphics g,JPanel pane){
        if (camera == null){
            System.out.println("UNABLE TO RENDER WITHOUT CAMERA!");
            return;
        }
        //Make sure no spats are rendered twice as chunks may contain repeats
        HashMap<Integer,RenderSpatial> temp = new HashMap<>();
        /*Note: Render System divides map up into quadrant sections and then renders whatever quadrants the camera captures*/
        for (int i = (int)(Math.floor(camera.getX()*S_QUAD));i < (int)((camera.getX()+camera.getLength())*S_QUAD);i++){
            for (int j = (int)(Math.floor(camera.getY()*S_QUAD)); j < (int)((camera.getY()+camera.getWidth())*S_QUAD);j++){
                HashMap<Integer,RenderSpatial> myobjs = renderMap[j][i].getObjects();
                RenderSpatial[] potentialRenders = myobjs.values().toArray(new RenderSpatial[0]);
                //potential renders because not all will be "rendered"
                for (int k = 0; k < potentialRenders.length;k++){
                    //add to renderlist and add into hashmap that it's been rendered
                    if (!temp.containsKey(potentialRenders[k].getId())){
                        renderObjects.add(potentialRenders[k]);
                        temp.put(potentialRenders[k].getId(),potentialRenders[k]);
                    }
                }
            }
        }
        System.out.println("Objects on screen:"+renderObjects.size());
        while (renderObjects.peek()!= null){
            renderObjects.poll().render(g,pane,camera);
        }
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
                addSpatial(myspat);
            }
        }
    }
    
    public void addSpatial(RenderSpatial spat){
        GamePoint location = spat.getLocation();
        int x = (int)Math.floor(location.getX()*S_QUAD);
        int y = (int)Math.floor(location.getZ()*S_QUAD);
        BoundingBox dims = spat.getDimensions();
        int sizex = (int)Math.ceil(dims.getLength()*S_QUAD);
        int sizey = (int)Math.ceil(dims.getWidth()*S_QUAD);

        for (int i = x; i <x+sizex;i++){
            for (int j = y; j < y+sizey;j++){
                //System.out.println(j+" "+i);
                renderMap[j][i].addObject(spat);
            }
        }
    }
    public void removeSpatial(RenderSpatial spat){
        GamePoint location = spat.getLocation();
        int x = (int)Math.floor(location.getX());
        int y = (int)Math.ceil(location.getZ());
        BoundingBox dims = spat.getDimensions();
        int sizex = (int)Math.ceil(dims.getLength()*S_QUAD);
        int sizey = (int)Math.ceil(dims.getWidth()*S_QUAD);
        for (int i = x; i <x+sizex;i++){
            for (int j = y; j < y+sizey;j++){
                renderMap[j][i].removeObject(spat);
            }
        }
    }
    public void setCam(AetherCam camera){
        this.camera = camera;
    }
    public void translateCamLocation(int x,int y){
        //if (camera.getX()+x+camera.getLength() < map.getDimX() && camera.getX()+x >= 0){
            camera.updatePosition(Math.max(Math.min(camera.getX()+x,map.getDimX()-camera.getLength()),0),Math.max(Math.min(camera.getY()+y,map.getDimY()-camera.getWidth()),0));
       //     System.out.println("OKAY!");
        //}if (camera.getY()+y+camera.getWidth() < map.getDimY() && camera.getY()+y >= 0){
          //  camera.updatePosition(0,camera.getY()+y);
        //}
    }
}
