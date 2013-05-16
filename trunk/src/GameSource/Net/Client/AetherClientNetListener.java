/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

import GameSource.Game.ClientWorldHandler;
import Networking.Client.Client;
import Networking.Client.ClientListenThread;
import Networking.Messages.LoginReplyMessage;
import Networking.Messages.MapDataPackageMessage;
import Networking.Messages.Message;
import Networking.Messages.PlayerJoinMessage;
import PhysicsSync.PhysicSyncMessage;
import Spatial.MapSpatData;
import Spatial.Spatial;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public class AetherClientNetListener extends ClientListenThread{
    private ClientWorldHandler world;
    
    public AetherClientNetListener(Socket csocket,Client client){
        super(csocket,client);
    }
    
    public void setWorld(ClientWorldHandler world){
        this.world = world;
    }

    @Override
    public void ReceiveMessage(Message msg) {
        if (msg instanceof MapDataPackageMessage){
            MapDataPackageMessage mymsg = (MapDataPackageMessage)msg;
            world.setGameMap(mymsg.getMapId());
            MapSpatData[] allSpats = mymsg.getSpatData();
            world.addAllSpatials(allSpats);
            System.out.println("Map Data Added!");
        } else if (msg instanceof PlayerJoinMessage) {
            PlayerJoinMessage mymsg = (PlayerJoinMessage)msg;
            Spatial newSpatial = world.addPlayerSpatial(mymsg);
            if (mymsg.getClientId() == client.getClientId()){
                System.out.println("Loaded in Player!");
                world.bindPlayerToClient(newSpatial);
                //as soon as we receive our spatial, we can display the game 
                //to the player C:
//                world.setGameWaiting(false);
                world.startGame();
            }
        }else if (msg instanceof LoginReplyMessage){
            System.out.println("logging in!");
            LoginReplyMessage mymsg = (LoginReplyMessage)msg;
            if (mymsg.getReply()){
                world.login();
            } else {
                world.setResponse("Failed to login. Try Again?");
            }
            //world.response = true;
        }else if (msg instanceof PhysicSyncMessage){
            world.addPSyncMessage((PhysicSyncMessage)msg);
        }
    }

//    @Override
//    public void onConnect() {
//        sendMessage(new ClientJoinChatMessage(userid));
//    }
    
//    public void setUserID(String id){
//        userid = id;
//    }

    
}
