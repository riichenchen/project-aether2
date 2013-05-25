/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Server;

import GameSource.Game.ServerWorldHandler;
import Networking.Messages.LoginReplyMessage;
import Networking.Messages.MapDataPackageMessage;
import Networking.Messages.Message;
import Networking.Messages.RequestLoginMessage;
import Networking.Server.ClientManager;
import Networking.Server.ServerNetListener;
import PhysicsSync.PhysicSyncMessage;

/**
 *
 * @author Shiyang
 */
public class AetherNetListener extends ServerNetListener{
    private ServerWorldHandler world;
    
    public AetherNetListener(ClientManager manager){//,ServerWorldHandler world){
        super(manager);
        //this.world = world;
    }
    public void setWorld(ServerWorldHandler World){
        this.world = World;
    }
    @Override
    public void ReceiveMessage(Message m){
        if (m instanceof PhysicSyncMessage){
            world.addPSyncMessage((PhysicSyncMessage)m);
        } else if (m instanceof RequestLoginMessage){
            RequestLoginMessage mymsg = (RequestLoginMessage)m;
            int reply = world.RequestLogin(mymsg.getUser(), mymsg.getPass());
            if (reply != -1){
                System.out.println(mymsg.getUser()+" has logged in as client "+mymsg.getClientId()+".");
                manager.sendToOne(mymsg.getClientId(), new LoginReplyMessage(true));
//                System.out.println("Sent o.o "+mymsg.getClientId());
                //Change this in the future
                manager.sendToOne(mymsg.getClientId(), new MapDataPackageMessage(world.getNonPermanents(),"testMap"));
                
                manager.broadcast(world.getPlayerJoinMessage(reply,mymsg.getClientId()));
            } else {
                System.out.println("Failed to login from client "+mymsg.getClientId()+".");
                manager.sendToOne(mymsg.getClientId(), new LoginReplyMessage(false));
            }
        } 
    }
}
