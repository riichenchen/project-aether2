/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Server;

import Networking.Messages.ChatMessage;
import Networking.Messages.ClientJoinChatMessage;
import Networking.Messages.Message;
import Networking.Server.ClientManager;
import Networking.Server.ServerClient;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public class ServerNetListener extends ServerClient{
    public ServerNetListener(Socket cSocket, ClientManager manager){
        super(cSocket,manager);
    }
    @Override
    public void ReceiveMessage(Message m) {
        if (m instanceof ChatMessage){
            ChatMessage mymsg = (ChatMessage)m;
            System.out.println(mymsg.getName()+": "+mymsg.getMessage());
            manager.broadcast(mymsg);
        } else if (m instanceof ClientJoinChatMessage){
            ClientJoinChatMessage mymsg = (ClientJoinChatMessage)m;
            System.out.println(mymsg.getName()+" has joined the chatroom");
            manager.broadcast(new ChatMessage("Server",mymsg.getName()+" has joined the chat!"));
        }
    }
    
}
