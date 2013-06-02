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
 *
 * @author Shiyang
 */
public class AetherNetListener extends ServerNetListener{
    private ServerWorldHandler world;
    
    public AetherNetListener(ClientManager manager){
        super(manager);
    }
    public void setWorld(ServerWorldHandler World){
        this.world = World;
    }
    @Override
    public void ReceiveMessage(Message m){
        if (m instanceof RequestLoginMessage){
            RequestLoginMessage mymsg = (RequestLoginMessage)m;
            
            LoginReply reply = world.RequestLogin(mymsg.getUser(), mymsg.getPass());
            if (reply.isAccepted()){
                System.out.println(mymsg.getUser()+" has logged in as client "+mymsg.getClientId()+".");
                manager.sendToOne(mymsg.getClientId(), new LoginReplyMessage(true,""));

                PlayerData pData = world.getPlayerData(reply.getAccId());
                
                PlayerJoinMessage newPlayer = new PlayerJoinMessage(pData);
                manager.sendToOne(mymsg.getClientId(), newPlayer);
                
            } else {
                System.out.println("Failed to login from client "+mymsg.getClientId()+".");
                manager.sendToOne(mymsg.getClientId(), new LoginReplyMessage(false,reply.getMessage()));
            }
        } else if (m instanceof SaveMessage){
            SaveMessage mymsg = (SaveMessage)m;
            //TODO: IMPLEMENT A SAVE :DDD
            System.out.println("Disconnect, let's save! Account: "+mymsg.getSaveData().getAccountId());
            world.savePlayerData(mymsg.getSaveData());
            //world.logOut(mymsg.getSaveData().getAccountId());
        }
    }
}
