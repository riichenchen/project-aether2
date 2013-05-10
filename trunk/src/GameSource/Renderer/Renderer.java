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
    private final float SX = 0.2f, SY = 0.2f;
    
    private GameMap map;
    
    public Renderer(GameMap map){
        this.map = map;
        this.renderMap = new RenderChunk[(int)(map.getDimY()*SX)][(int)(map.getDimX()*SY)];
        for (int i = 0; i < (int)(map.getDimY()*SY);i++){
            for (int j = 0; j < (int)(map.getDimX()*SX);j++){
                renderMap[i][j] = new RenderChunk();
            }
        }
    }
    
    public void render(Graphics g,JPanel pane){
        if (camera == null){
            System.out.println("UNABLE TO RENDER WITHOUT CAMERA!");
            return;
        }
        /*Note: Render System divides map up into quadrant sections and then renders whatever quadrants the camera captures*/
        for (int i = (int)(Math.floor(camera.getX()*SX));i < (int)((camera.getX()+camera.getLength())*SX);i++){
            for (int j = (int)(Math.floor(camera.getY()*SY)); j < (int)((camera.getY()+camera.getWidth())*SY);j++){
                HashMap<Integer,RenderSpatial> myobjs = renderMap[j][i].getObjects();
                RenderSpatial[] meep = myobjs.values().toArray(new RenderSpatial[0]);
                
                for (int k = 0; k < meep.length;k++){
                    renderObjects.add(meep[k]);
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
        int x = (int)Math.floor(location.getX()*SX);
        int y = (int)Math.floor(location.getZ()*SY);
        BoundingBox dims = spat.getDimensions();
        int sizex = (int)Math.ceil(dims.getLength()*SX);
        int sizey = (int)Math.ceil(dims.getWidth()*SY);

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
        int sizex = (int)Math.ceil(dims.getLength()*SX);
        int sizey = (int)Math.ceil(dims.getWidth()*SY);
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
