/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Server;

import Networking.Messages.ChatMessage;
import Networking.Messages.ClientJoinChatMessage;
import Networking.Messages.LoginReplyMessage;
import Networking.Messages.Message;
import Networking.Messages.RequestLoginMessage;
import Networking.Server.ClientManager;
import Networking.Server.ServerClient;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public class ServerNetListener extends ServerClient{
    private ServerTest world;
    public ServerNetListener(Socket cSocket, ClientManager manager){//, ServerTest world){
        super(cSocket,manager);
        //this.world = world;
    }
    @Override
    public void ReceiveMessage(Message m) {
//        if (m instanceof RequestLoginMessage){
//            RequestLoginMessage mymsg = (RequestLoginMessage)m;
//            if (world.RequestLogin(mymsg.getUser(),mymsg.getPass())){
//                manager.sendToClient(m.getClientId(),new LoginReplyMessage(true));
//            } else {
//                manager.sendToClient(m.getClientId(),new LoginReplyMessage(false));
//            }
//            //manager.reply
//        } else 
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
        }
    }
    
}
