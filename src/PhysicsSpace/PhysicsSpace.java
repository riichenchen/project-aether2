/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSpace;

import GameSource.Globals;
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
                }
            }
        }
        
        Spatial[] pAreaSpats = PlayerSpatialsInArea.values().toArray(new Spatial[0]);
        Spatial[] envAreaSpats = EnvSpatialsInArea.values().toArray(new Spatial[0]);

        return new Spatial[][]{pAreaSpats,envAreaSpats};
    }
//    private int n = 0;
    public void update(){
        resolveMessages();
        Spatial[] allPlayersArray = playerSpats.values().toArray(new Spatial[0]);
        int n = 0;
        for (int i = 0; i < allPlayersArray.length;i++){
            Spatial[][] spatsAround = grabSpatialsAround(allPlayersArray[i]);
            Spatial[] playerSpats = spatsAround[0];
            Spatial[] envSpats = spatsAround[1];
//            System.out.println(Arrays.toString(playerSpats));
            for (int j = 0; j < playerSpats.length;j++){
                for (int k = j+1; k < playerSpats.length;k++){
                    if (playerSpats[j].collide(playerSpats[k])){
                        playerSpats[j].collideEffect(playerSpats[k]);
                        //Note: the call to the second will be
                        //executed when "i" loops around to it in theory
                        //May have to uncomment the below line if not.
//                        playerSpats[k].collideEffect(playerSpats[j]);
                    }
                    n++; //Operation counter for debug
                }
                for (int l = 0; l < envSpats.length;l++){
                    if (playerSpats[j].collide(envSpats[l])){
//                        System.out.println("YAY"+(n++));
                        playerSpats[j].collideEffect(envSpats[l]);
                        envSpats[l].collideEffect(playerSpats[j]);
                    }
                    n++;
                }
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
            }
        }
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
    
    public void moveSpatial(Spatial s, float x, float y, float z){
        s.move(x,y,z);
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
    
//    public void addMoveMessage(float x, float z, Spatial spat){
//        PhysicsSpaceMessage message = new PhysicsSpaceMessage(x,z,spat);
//        msgs.addLast(message);
//    }
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
