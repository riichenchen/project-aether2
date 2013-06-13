/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Server;

import Database.LoginReply;
import Database.PlayerData;
import GameSource.Game.ServerWorldHandler;
import Networking.Messages.LoginReplyMessage;
import Networking.Messages.Message;
import Networking.Messages.PlayerJoinMessage;
import Networking.Messages.RequestLoginMessage;
import Networking.Messages.SaveMessage;
import Networking.Server.ClientManager;
import Networking.Server.ServerNetListener;

/**
 * @author Shiyang
 * The AetherNetListener handles all server network listening.
 * It takes in requests and deals with them appropriately, sending
 * a reply back.
 */
public class AetherNetListener extends ServerNetListener{
    private ServerWorldHandler world;
    
    public AetherNetListener(ClientManager manager){
        super(manager);
    }
    //bind to the serverworldhandler
    public void setWorld(ServerWorldHandler World){
        this.world = World;
    }
    
    @Override
    public void ReceiveMessage(Message m){
        //Request for login; attempts to login and returns a reply back
        if (m instanceof RequestLoginMessage){
            RequestLoginMessage mymsg = (RequestLoginMessage)m;
            
            LoginReply reply = world.RequestLogin(mymsg.getUser(), mymsg.getPass());
            if (reply.isAccepted()){ // if it's accepted, return accepted and send all player data
                System.out.println(mymsg.getUser()+" has logged in as client "+mymsg.getClientId()+".");
                manager.sendToOne(mymsg.getClientId(), new LoginReplyMessage(true,""));

                PlayerData pData = world.getPlayerData(reply.getAccId());
                
                PlayerJoinMessage newPlayer = new PlayerJoinMessage(pData);
                manager.sendToOne(mymsg.getClientId(), newPlayer);
                
            } else {
                System.out.println("Failed to login from client "+mymsg.getClientId()+".");
                manager.sendToOne(mymsg.getClientId(), new LoginReplyMessage(false,reply.getMessage()));
            }
        // Save request - record al information to the database through the serverworldhandler
        } else if (m instanceof SaveMessage){
            SaveMessage mymsg = (SaveMessage)m;
            System.out.println("Disconnect, let's save! Account: "+mymsg.getSaveData().getAccountId());
            world.savePlayerData(mymsg.getSaveData());
        }
    }
}
