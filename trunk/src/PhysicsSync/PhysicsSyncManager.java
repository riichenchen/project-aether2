/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSync;

import GameSource.Game.ServerWorldHandler;
import GameSource.Globals;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * This is the physics sync manager for the network
 * all syncing messages go through here where they are
 * treated by the world handler.
 * A priority queue is used to sort messages out by time
 * @author Shiyang
 */
public class PhysicsSyncManager {
    //Note: Hypothetically speaking, the order that things appear
    //should be in the correct time order. But for future purposes
    //A priorityqueue is set up.
    private PriorityQueue<PhysicSyncMessage> allMessages;
    private ServerWorldHandler world;
    private int TimeElapsed;
    public PhysicsSyncManager(ServerWorldHandler world){
        allMessages = new PriorityQueue<>(10,new TimePriorityComparator());
        this.world = world;
    }
    public synchronized void addMessage(PhysicSyncMessage msg){
        msg.setReceiveTime(TimeElapsed);
        allMessages.add(msg);
    }
    public synchronized void elapseTime(){
        TimeElapsed++;
    }
    
    public synchronized void update(){ // okay so only main should be calling this anyhow... but better thread safe than not
        while (allMessages.peek()!=null){
            if (TimeElapsed-allMessages.peek().getReceivedTime()<3){
                if (Globals.P_SYNC_DEBUG){
                    System.out.println("Warning: Message time received a tad too soon for liking.");
                }
                break;
            }
            PhysicSyncMessage msg = allMessages.poll();
            if (msg instanceof SpatActionMessage){
                SpatActionMessage mymsg = (SpatActionMessage)msg;
                //mymsg.doAction(world.getSpat(msg.getSpatId()))
            }
        }
    }
    
}
