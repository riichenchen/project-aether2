/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSpace;

import GameSource.Game.GamePoint;
import GameSource.Globals;
import Math.Line2D;
import Math.Point2D;
import Math.Vector2D;
import Spatial.Spatial;
import java.util.*;

/**
 *
 * @author Robert
 * The big boss physics space, that handles all physics operations, including
 * collision detection and grabbing spatials around 1 spatial.
 * Provides some useful methods that any spatial affecting class will need.
 */
public class PhysicsSpace {
    public HashMap <Integer,Spatial> spatials;
    public LinkedList <PhysicsSpaceMessage> msgs;
    private PhysicsChunk[][] physicsMap;
    public final float gravity; // not implemented atm
    public final int length, width;
    private int n = 0;//use for debugging number of operations
    
    //Constants represent a scale factor by which to divide the entire "map" into
    //and organize spatials by      -taken from Renderer.java (ask shiyang)
    private final float S_QUAD = 0.10f;
    
    private HashMap<Integer,Spatial> enviroSpats;
    private HashMap<Integer,Spatial> playerSpats;
    
    public PhysicsSpace(float g, int l, int w){
        length = l;
        width = w;
        gravity = g;
        spatials = new HashMap <>();
        enviroSpats = new HashMap<>();
        playerSpats = new HashMap<>();
        msgs = new LinkedList <>();
        this.physicsMap = new PhysicsChunk[(int)(w*S_QUAD)][(int)(l*S_QUAD)];
        for (int i = 0; i < (int)(w*S_QUAD);i++){
            for (int j = 0; j < (int)(l*S_QUAD);j++){
                physicsMap[i][j] = new PhysicsChunk();
            }
        }
    }
    
    public void updateSpatial(Spatial spat){
        msgs.add(new PUpdateSpatialMessage(spat));
    }
    
    //Uses the spatial's culling box to grab all spatials that fall
    //in this spatial's area
    public Spatial[][] grabSpatialsAround(Spatial spat){
        int[] cullbounds = spat.getCullBounds(S_QUAD);//GamePoint location = spat.getLocation();
        int x = cullbounds[0];
        int y = cullbounds[1];
        int sizex = cullbounds[2];
        int sizey = cullbounds[3];
        HashMap<Integer,Spatial> EnvSpatialsInArea = new HashMap<>();
        HashMap<Integer,Spatial> PlayerSpatialsInArea = new HashMap<>();
        for (int i = x; i <x+sizex;i++){ // browse through all spatialls that fall in chunks in the cullbounds
            for (int j = y; j < y+sizey;j++){
                Spatial[] chunkSpats = physicsMap[j][i].getObjects().values().toArray(new Spatial[0]);
                for (Spatial s: chunkSpats){
                    if (!EnvSpatialsInArea.containsKey(s.getId()) && enviroSpats.containsKey(s.getId())){
                        EnvSpatialsInArea.put(s.getId(), s);
                    }
                    if (!PlayerSpatialsInArea.containsKey(s.getId()) && playerSpats.containsKey(s.getId())){
                        PlayerSpatialsInArea.put(s.getId(), s);
                    }
                    n++;
                }
            }
        }
        
        Spatial[] pAreaSpats = PlayerSpatialsInArea.values().toArray(new Spatial[0]);
        Spatial[] envAreaSpats = EnvSpatialsInArea.values().toArray(new Spatial[0]);

        return new Spatial[][]{pAreaSpats,envAreaSpats};
    }

    //The update method checks for all collisions that occur regarding all player spatials
    // as well as resolves all previous physics messages
    public void update(){
        resolveMessages();
        Spatial[] allPlayersArray = playerSpats.values().toArray(new Spatial[0]);
        n = 0;
        for (int i = 0; i < allPlayersArray.length;i++){
            //parse through each spatial, grabbing spats around it and checking for
            //collisions
            Spatial[][] spatsAround = grabSpatialsAround(allPlayersArray[i]);
            Spatial[] pSpats = spatsAround[0];
            Spatial[] envSpats = spatsAround[1];
            for (int j = 0; j < pSpats.length; j++){
                if (allPlayersArray[i].collide(pSpats[j])){
                    allPlayersArray[i].collideEffect(pSpats[j]);
                }   
            }
            for (int l = 0; l < envSpats.length;l++){
                if (allPlayersArray[i].collide(envSpats[l])){
                    allPlayersArray[i].collideEffect(envSpats[l]);
                    envSpats[l].collideEffect(allPlayersArray[i]);
                }
                n++;
            }
        }
        if (Globals.__PHYSICSDEBUG__ < 4){
            System.out.println("Number of operations: "+n);
        }
    }
    
    //the resolve messages method takes care of all messages in the messagepump
    //updating a spatial's chunks as well as moving it.
    public void resolveMessages(){
        while (msgs.peekFirst() != null){
            PhysicsSpaceMessage message = msgs.poll();
            if (message instanceof PUpdateSpatialMessage){
                //update our spatial into it's new chunk positions
                Spatial myspat = ((PUpdateSpatialMessage)message).spat;
                performUpdateSpatial(myspat);
                
            } else if (message instanceof PMoveSpatialMessage){
                //Move the spatial to it's new position
                PMoveSpatialMessage mymsg = (PMoveSpatialMessage)message;
                Vector2D vel = mymsg.getMotion();
                GamePoint pos = mymsg.spat.getLocation();
                
                Point2D pt = new Point2D(pos.getX(),pos.getZ());
                Point2D newPt = new Point2D(pos.getX()+vel.x,pos.getZ()+vel.z);
                
                mymsg.spat.setLocation(newPt.x,pos.getY(),newPt.y);
                performUpdateSpatial(message.spat);
            }
        }
    }
    
    //clears all messages to maintain synchronization
    public void clearMessages(){
        msgs.clear();
    }
    
    
    //This method performs the actual spatial updating.
    private void performUpdateSpatial(Spatial spat){
        PhysicsChunk[] chunks = spat.getPhysicsChunks().values().toArray(new PhysicsChunk[0]);
        for (PhysicsChunk chunk: chunks){ // clear all spats from current chunk
            chunk.removeObject(spat);
        }
        spat.getPhysicsChunks().clear();//removes all chunks that belong to the player
        addSpatial(spat);//readd the player
    }
    //Enviro spats won't check for collision with each other
    public void addEnviroSpatial(Spatial s){
        enviroSpats.put(s.getId(), s);
        addSpatial(s);
    }
    
    public void addPlayerSpatial(Spatial s){
        playerSpats.put(s.getId(), s);
        addSpatial(s);
    }
    
    //takes a spatial and binds it to the physics space
    public void addSpatial(Spatial spat){
        //give spat reference to this
        spat.bindToSpace(this);
        spatials.put(spat.getId(), spat);
        int[] cullbounds = spat.getCullBounds(S_QUAD);
        int x = cullbounds[0];
        int y = cullbounds[1];
        int sizex = cullbounds[2];
        int sizey = cullbounds[3];
        
        for (int i = x; i <x+sizex;i++){
            for (int j = y; j < y+sizey;j++){
                physicsMap[j][i].addObject(spat); // add the spatial to all chunks it belongs in
            }
        }
    }
    
    //The remove spatial method removes a given spatial from the physics space
    public void removeSpatial(Spatial spat){
        spatials.remove(spat.getId());
        enviroSpats.remove(spat.getId());
        playerSpats.remove(spat.getId());
        int[] cullbounds = spat.getCullBounds(S_QUAD);//GamePoint location = spat.getLocation();
        int x = cullbounds[0];
        int y = cullbounds[1];
        int sizex = cullbounds[2];
        int sizey = cullbounds[3];
        
        for (int i = x; i <x+sizex;i++){
            for (int j = y; j < y+sizey;j++){
                physicsMap[j][i].removeObject(spat);
            }
        }
        spat.unbindFromSpace();
    }
    //Ray cast methods take in two points, p1 and p2 or 4 coordinates representing
    //2 points and grabs all spatials that fall within chunks captured by the ray
    public Spatial[] rayCast(Point2D p1,Point2D p2){
        return rayCast(p1.x,p1.y,p2.x,p2.y);
    }
    
    public Spatial[] rayCast(float x1,float y1,float x2,float y2){
        HashMap<Integer,Spatial> hit = new HashMap<>();
        HashMap<Integer,Spatial> checked = new HashMap<>();
        Point2D pointA = new Point2D(x1,y1);
        Point2D pointB = new Point2D(x2,y2);
        Line2D ray = new Line2D(pointA,pointB);
        int increment = (int)(1/S_QUAD);
        
        if (!ray.isUndefined() && Math.abs(ray.getSlope()) >= 1){ // case 1: the slope is greater than 45 degrees
            for (int i = (int)Math.min(y1,y2);i < (int)Math.max(y1,y2); i+=increment){
                Line2D newline = new Line2D(new Point2D(0,i),new Point2D(width,i));
                Point2D intersection = ray.intersect(newline);
                int chunkY = (int)(intersection.y*S_QUAD);
                int chunkX = (int)(intersection.x*S_QUAD);
                
                if (chunkY < physicsMap.length && chunkX < physicsMap[0].length && chunkY>=0 && chunkX>=0){
                    for (Spatial spat: physicsMap[chunkY][chunkX].getObjects().values().toArray(new Spatial[0])){
                        if (!checked.containsKey(spat.getId())){
                            checked.put(spat.getId(), spat);
                            Point2D[] intersections = spat.intersectLine(ray); // interpolate the line's intersection with a chunk line
                            if (intersections.length > 0){// make sure we captured at least 1 spatial
                                hit.put(spat.getId(), spat);
                            }
                        }
                    }
                }
            }
        } else if (!ray.isUndefined()){ // case two: the line's slope is less than 45 degrees
            for (int i = (int)Math.min(x1,x2);i < (int)Math.max(x1,x2); i+=increment){
                Line2D newline = new Line2D(new Point2D(i,0),new Point2D(i,length));
                Point2D intersection = ray.intersect(newline);
                int chunkY = (int)(intersection.y*S_QUAD); // chunk x and y dimensions
                int chunkX = (int)(intersection.x*S_QUAD);
                
                if (chunkY < physicsMap.length && chunkX < physicsMap[0].length && chunkY>=0 && chunkX>=0){
                    for (Spatial spat: physicsMap[chunkY][chunkX].getObjects().values().toArray(new Spatial[0])){
                        if (!checked.containsKey(spat.getId())){
                            checked.put(spat.getId(), spat);
                            Point2D[] intersections = spat.intersectLine(ray); // interpolate the line's intersection with a chunk line
                            if (intersections.length > 0){// make sure we captured at least 1 spatial
                                hit.put(spat.getId(), spat);
                            }
                        }
                    }
                }
            }
        } else { // the ray is undefined
            for (int i = (int)Math.min(y1, y2) ; i < (int)Math.max(y1,y2);i+=increment){
                int chunkY = (int)(i*S_QUAD);
                int chunkX = (int)(x1*S_QUAD);
                
                if (chunkY < physicsMap.length && chunkX < physicsMap[0].length && chunkY>=0 && chunkX>=0){
                    for (Spatial spat: physicsMap[chunkY][chunkX].getObjects().values().toArray(new Spatial[0])){
                        if (!checked.containsKey(spat.getId())){
                            checked.put(spat.getId(), spat);
                            Point2D[] intersections = spat.intersectLine(ray); // interpolate the line's intersection with a chunk line
                            if (intersections.length > 0){// make sure we captured at least 1 spatial
                                hit.put(spat.getId(), spat);
                            }
                        }
                    }
                }
            }
        }
        return hit.values().toArray(new Spatial[0]);
    }
    
    //Adds a move message into the message queue
    public void addMoveMessage(float x, float z, Spatial spat){
        PMoveSpatialMessage message = new PMoveSpatialMessage(spat,new Vector2D(x,0,z));
        msgs.addLast(message);
    }
}
