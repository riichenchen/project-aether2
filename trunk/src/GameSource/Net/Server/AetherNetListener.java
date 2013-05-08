/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Server;

import Networking.Messages.ChatMessage;
import Networking.Messages.ClientJoinChatMessage;
import Networking.Messages.Message;
import Networking.Messages.RequestLoginMessage;
import Networking.Server.ClientManager;
import Networking.Server.ServerNetListener;

/**
 *
 * @author Shiyang
 */
public class AetherNetListener extends ServerNetListener{
    private ServerTest world;
    public AetherNetListener(ClientManager manager){
        super(manager);
        //this.world = world;
    }
    
    @Override
    public void ReceiveMessage(Message m){
        if (m instanceof ChatMessage){
            ChatMessage mymsg = (ChatMessage)m;
            System.out.println(mymsg.getName()+": "+mymsg.getMessage());
            manager.broadcast(mymsg);
        } else if (m instanceof ClientJoinChatMessage){
            ClientJoinChatMessage mymsg = (ClientJoinChatMessage)m;
            System.out.println(mymsg.getName()+" has joined the chatroom");
            ChatMessage serverMsg = new ChatMessage("Server",mymsg.getName()+" has joined the chat!");
            serverMsg.setClientId(-1);
            manager.broadcast(serverMsg);
        } else if (m instanceof RequestLoginMessage){
            RequestLoginMessage mymsg = (RequestLoginMessage)m;
            //System.out.println(world);
            if (world.RequestLogin(mymsg.getUser(), mymsg.getPass())){
                System.out.println("You're in!");
            } else {
                System.out.println("You're not :C");
            }
        }
    }
    public void setWorld(ServerTest world){
        this.world = world;
    }
}
