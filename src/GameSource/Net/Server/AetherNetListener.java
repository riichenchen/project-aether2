/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Server;

import Database.PlayerData;
import GameSource.Assets.AssetManager;
import GameSource.Game.ServerWorldHandler;
import Networking.Messages.DisconnectMessage;
import Networking.Messages.LoginReplyMessage;
import Networking.Messages.MapDataPackageMessage;
import Networking.Messages.Message;
import Networking.Messages.PlayerJoinMessage;
import Networking.Messages.RemoveSpatialMessage;
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
                PlayerData pData = world.getPlayerData(reply);
                String mapid = pData.getMapId();
                manager.sendToOne(mymsg.getClientId(), new MapDataPackageMessage(world.getNonPermanents(mapid),mapid));
                
                PlayerJoinMessage newPlayer = world.getPlayerJoinMessage(pData,mymsg.getClientId());
                world.sendToMap(mapid,newPlayer);
                //manager.broadcast(newPlayer);
                
            } else {
                System.out.println("Failed to login from client "+mymsg.getClientId()+".");
                manager.sendToOne(mymsg.getClientId(), new LoginReplyMessage(false));
            }
        } else if (m instanceof DisconnectMessage){
            DisconnectMessage mymsg = (DisconnectMessage)m;
            AssetManager.getMap(mymsg.getMapId()).removeSpatial(mymsg.getSpatId());
            AssetManager.getMap(mymsg.getMapId()).removeClient(mymsg.getClientId());
            world.sendToMap(mymsg.getMapId(), new RemoveSpatialMessage(mymsg.getSpatId(),mymsg.getMapId()));
        }
    }
}
