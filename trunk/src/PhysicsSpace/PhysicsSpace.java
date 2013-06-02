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
 */
public class PhysicsSpace {
    public HashMap <Integer,Spatial> spatials;
    public LinkedList <PhysicsSpaceMessage> msgs;
    private PhysicsChunk[][] physicsMap;
    public final float gravity;
    public final int length, width;
    
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
//    public void gravityEffect(){
//        Spatial[] spatialsArray = spatials.values().toArray(new Spatial[0]);
//        for (Spatial s: spatialsArray){
//            addGravityMessage(s.mass * gravity * -1,s);
//        }
//    }
    public boolean checkCollision(Spatial a){
    	Spatial[] spatialsArray = spatials.values().toArray(new Spatial[0]);
        for (Spatial b: spatialsArray){
            if (! a.equals(b)){
                if (a.collide(b) || b.collide(a)){
                    a.collideEffect(b);
                    b.collideEffect(a);
                    if (a.collidable == b.collidable){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void updateSpatial(Spatial spat){
        msgs.add(new PUpdateSpatialMessage(spat));
    }
    
    //Uses the spatial's culling box to grab all spatials that fall
    //in this spatial's area
    public Spatial[][] grabSpatialsAround(Spatial spat){
        int[] cullbounds = spat.getCullBounds(S_QUAD);//GamePoint location = spat.getLocation();
        int x = cullbounds[0];//(int)Math.floor(location.getX()*S_QUAD);
        int y = cullbounds[1];//(int)Math.floor(location.getZ()*S_QUAD);
        int sizex = cullbounds[2];//(int)Math.ceil(spat.getLength()*S_QUAD);
        int sizey = cullbounds[3];//(int)Math.ceil(spat.getWidth()*S_QUAD);
        HashMap<Integer,Spatial> EnvSpatialsInArea = new HashMap<>();
        HashMap<Integer,Spatial> PlayerSpatialsInArea = new HashMap<>();
        for (int i = x; i <x+sizex;i++){
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
    private int n = 0;
    int derp = 0;
    public void update(){
        resolveMessages();
        Spatial[] allPlayersArray = playerSpats.values().toArray(new Spatial[0]);
        n = 0;
        for (int i = 0; i < allPlayersArray.length;i++){
            Spatial[][] spatsAround = grabSpatialsAround(allPlayersArray[i]);
            Spatial[] pSpats = spatsAround[0];
            Spatial[] envSpats = spatsAround[1];
//            System.out.println(Arrays.toString(playerSpats));
            for (int j = 0; j < pSpats.length; j++){
                if (allPlayersArray[i].collide(pSpats[j])){
                    allPlayersArray[i].collideEffect(pSpats[j]);
                }   
            }
//            for (int j = 0; j < pSpats.length;j++){
//                for (int k = j+1; k < pSpats.length;k++){
//                    if (pSpats[j].collide(pSpats[k])){
//                        pSpats[j].collideEffect(pSpats[k]);
//                        //Note: the call to the second will be
//                        //executed when "i" loops around to it in theory
//                        //May have to uncomment the below line if not.
//                        pSpats[k].collideEffect(pSpats[j]);
//                    }
//                    n++; //Operation counter for debug
//                }
//            }
//            System.out.println(Arrays.toString(envSpats));
            for (int l = 0; l < envSpats.length;l++){
                if (allPlayersArray[i].collide(envSpats[l])){
//                        System.out.println("YAY"+(n++));
                    allPlayersArray[i].collideEffect(envSpats[l]);
                    envSpats[l].collideEffect(allPlayersArray[i]);
                }
                n++;
            }
        }
        if (Globals.__PHYSICSDEBUG__ < 4){
            System.out.println("Number of operations: "+n);
        }
//        gravityEffect();
    }
    
    public void resolveMessages(){
        while (msgs.peekFirst() != null){
            PhysicsSpaceMessage message = msgs.poll();
            if (message instanceof PUpdateSpatialMessage){
                Spatial myspat = ((PUpdateSpatialMessage)message).spat;
                performUpdateSpatial(myspat);
            } else if (message instanceof PMoveSpatialMessage){
                PMoveSpatialMessage mymsg = (PMoveSpatialMessage)message;
                Vector2D vel = mymsg.getMotion();
                GamePoint pos = mymsg.spat.getLocation();
                
                Point2D pt = new Point2D(pos.getX(),pos.getZ());
                Point2D newPt = new Point2D(pos.getX()+vel.x,pos.getZ()+vel.z);
                
//                Spatial[] hit = rayCast(pt,newPt);
//                
//                Line2D ln = new Line2D(pt,newPt);
//                Line2D faceLine = mymsg.spat.getBoundingLines()[0];
//                
//                Point2D closest = newPt;
//                double closestDist = faceLine.distanceTo(closest);
//                
//                float rot = (float)Math.toRadians(mymsg.spat.getRotation());
//                float wid = mymsg.spat.getWidth()/2;
//                //System.out.println(closestDist);
////                System.out.println(Arrays.toString(hit));
//                for (Spatial spat: hit){
//                   if (spat == mymsg.spat) {
//                        continue;
//                   }
//                   Point2D[] pts = spat.intersectLine(ln);
//                   System.out.println("HERP");
////                   System.out.println(pts.length);
//                   for (Point2D point: pts){
//                       System.out.println("-----");
//                       double testDist = faceLine.distanceTo(point);
//                       System.out.println(testDist+" "+closestDist);
//                       if (testDist-wid <= closestDist){
//                        
//                           closestDist = testDist;
//                           closest = point;
//                           closest.x -= wid*(float)Math.cos(rot);
//                           closest.y -= wid*(float)Math.sin(rot);
//                           System.out.println(testDist);
////                           System.out.println("uh oh");
////                           System.out.println(point.x+" "+point.y);
//                       }
//                   }
////                   pts = spat.getPoints();
////                   for (Point2D point: pts){
////                       double testDist = faceLine.distanceTo(point);
////                       if (testDist < closestDist){
////                           closestDist = testDist;
////                           closest = point;
////                       }
////                   }
//                }
////                System.out.println("Wid "+wid);
////                mymsg.spat.setLocation(new GamePoint(closest.x,pos.getY(),closest.y-wid*(float)Math.sin(rot)));
//                mymsg.spat.setLocation(new GamePoint(closest.x,pos.getY(),closest.y));
                mymsg.spat.setLocation(new GamePoint(newPt.x,pos.getY(),newPt.y));
                performUpdateSpatial(message.spat);
            }
        }
    }
    
    public void clearMessages(){
        msgs.clear();
    }
    
    private void performUpdateSpatial(Spatial spat){
        PhysicsChunk[] chunks = spat.getPhysicsChunks().values().toArray(new PhysicsChunk[0]);
        for (PhysicsChunk chunk: chunks){
            chunk.removeObject(spat);
        }
        spat.getPhysicsChunks().clear();
        addSpatial(spat);
    }
    public void addEnviroSpatial(Spatial s){
        enviroSpats.put(s.getId(), s);
        addSpatial(s);
    }
    
    public void addPlayerSpatial(Spatial s){
        playerSpats.put(s.getId(), s);
        addSpatial(s);
    }
    
    public void addSpatial(Spatial spat){
        spatials.put(spat.getId(), spat);
        int[] cullbounds = spat.getCullBounds(S_QUAD);
        int x = cullbounds[0];
        int y = cullbounds[1];
        int sizex = cullbounds[2];
        int sizey = cullbounds[3];
        
        for (int i = x; i <x+sizex;i++){
            for (int j = y; j < y+sizey;j++){
                physicsMap[j][i].addObject(spat);
            }
        }
        //give spat reference to this
        spat.bindToSpace(this);
    }
    
    public void removeSpatial(Spatial spat){
        spatials.remove(spat.getId());
        enviroSpats.remove(spat.getId());
        playerSpats.remove(spat.getId());
        int[] cullbounds = spat.getCullBounds(S_QUAD);//GamePoint location = spat.getLocation();
        int x = cullbounds[0];//(int)Math.floor(location.getX()*S_QUAD);
        int y = cullbounds[1];//(int)Math.floor(location.getZ()*S_QUAD);
        int sizex = cullbounds[2];//(int)Math.ceil(spat.getLength()*S_QUAD);
        int sizey = cullbounds[3];//(int)Math.ceil(spat.getWidth()*S_QUAD);
        
        for (int i = x; i <x+sizex;i++){
            for (int j = y; j < y+sizey;j++){
                physicsMap[j][i].removeObject(spat);
            }
        }
        spat.unbindFromSpace();
    }
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
        
        if (!ray.isUndefined() && Math.abs(ray.getSlope()) >= 1){
            for (int i = (int)Math.min(y1,y2);i < (int)Math.max(y1,y2); i+=increment){
                Line2D newline = new Line2D(new Point2D(0,i),new Point2D(width,i));
                Point2D intersection = ray.intersect(newline);
                int chunkY = (int)(intersection.y*S_QUAD);
                int chunkX = (int)(intersection.x*S_QUAD);
                
                if (chunkY < physicsMap.length && chunkX < physicsMap[0].length && chunkY>=0 && chunkX>=0){
                    for (Spatial spat: physicsMap[chunkY][chunkX].getObjects().values().toArray(new Spatial[0])){
                        if (!checked.containsKey(spat.getId())){
                            checked.put(spat.getId(), spat);
                            Point2D[] intersections = spat.intersectLine(ray);
                            if (intersections.length > 0){
                                hit.put(spat.getId(), spat);
                            }
                        }
                    }
                }
            }
        } else if (!ray.isUndefined()){
            for (int i = (int)Math.min(x1,x2);i < (int)Math.max(x1,x2); i+=increment){
                Line2D newline = new Line2D(new Point2D(i,0),new Point2D(i,length));
                Point2D intersection = ray.intersect(newline);
                int chunkY = (int)(intersection.y*S_QUAD);
                int chunkX = (int)(intersection.x*S_QUAD);
                
                if (chunkY < physicsMap.length && chunkX < physicsMap[0].length && chunkY>=0 && chunkX>=0){
                    for (Spatial spat: physicsMap[chunkY][chunkX].getObjects().values().toArray(new Spatial[0])){
                        if (!checked.containsKey(spat.getId())){
                            checked.put(spat.getId(), spat);
                            Point2D[] intersections = spat.intersectLine(ray);
                            if (intersections.length > 0){
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
                            Point2D[] intersections = spat.intersectLine(ray);
                            if (intersections.length > 0){
                                hit.put(spat.getId(), spat);
                            }
                        }
                    }
                }
            }
        }
        return hit.values().toArray(new Spatial[0]);
    }
    
    public void addMoveMessage(float x, float z, Spatial spat){
        PMoveSpatialMessage message = new PMoveSpatialMessage(spat,new Vector2D(x,0,z));
        msgs.addLast(message);
    }
//    
//    public void addGravityMessage(float y, Spatial spat){
//        PhysicsSpaceMessage message = new PhysicsSpaceMessage(y,spat);
//        msgs.addLast(message);
//    }
//    public void addJumpMessage(float y, Spatial spat){
//        PhysicsSpaceMessage message = new PhysicsSpaceMessage(y,spat);
//        msgs.addLast(message);
//    }
//    public void addRemoveMessage(Spatial spat){
//        PhysicsSpaceMessage message = new PhysicsSpaceMessage(spat);
//        msgs.addLast(message);
//    }
}
