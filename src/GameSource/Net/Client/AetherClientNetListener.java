/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

import GameSource.Assets.AssetManager;
import GameSource.Game.ClientWorldHandler;
import Networking.Client.Client;
import Networking.Client.ClientListenThread;
import Networking.Messages.LoginReplyMessage;
import Networking.Messages.MapDataPackageMessage;
import Networking.Messages.Message;
import Networking.Messages.PlayerJoinMessage;
import Networking.Messages.RemoveSpatialMessage;
import PhysicsSync.PhysicSyncMessage;
import Spatial.MapSpatData;
import Spatial.Spatial;
import java.net.Socket;

/**
 * @author Shiyang
 * The network message listener class. Listens for messages from the user
 * and then loads in the player using that information.
 */
public class AetherClientNetListener extends ClientListenThread{
    private ClientWorldHandler world;
    
    public AetherClientNetListener(Socket csocket,Client client){
        super(csocket,client);
    }
    
    public void setWorld(ClientWorldHandler world){
        this.world = world;
    }

    //Overridden receive message. Logs the user in and binds the client as well as loads
    //the character data.
    @Override
    public void ReceiveMessage(Message msg) {
        if (msg instanceof PlayerJoinMessage) {
            PlayerJoinMessage mymsg = (PlayerJoinMessage)msg;
            Spatial newSpatial = world.addPlayerSpatial(mymsg);
            System.out.println("Loaded in Player!");
            world.bindPlayerToClient(newSpatial);
            world.bindAccountToClient(mymsg.getpData().getAccountId());
            //as soon as we receive our spatial, we can display the game 
            //to the player C:
            world.startGame();
        }else if (msg instanceof LoginReplyMessage){
            LoginReplyMessage mymsg = (LoginReplyMessage)msg;
            if (mymsg.getReply()){
                world.login();
            } else {
                world.setResponse(mymsg.getMessage());
            }
        }
    }
}
