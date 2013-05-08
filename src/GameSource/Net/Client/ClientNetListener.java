/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

import GameSource.LoginFrame;
import Networking.Client.Client;
import Networking.Client.ClientListenThread;
import Networking.Messages.ChatMessage;
import Networking.Messages.LoginReplyMessage;
import Networking.Messages.Message;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public class ClientNetListener extends ClientListenThread{
    private LoginFrame world;
    
    public ClientNetListener(Socket csocket,Client client){
        super(csocket,client);
    }
    
    public void setWorld(LoginFrame world){
        this.world = world;
    }

    @Override
    public void ReceiveMessage(Message msg) {
        if (msg instanceof ChatMessage){
            ChatMessage mymsg = (ChatMessage)msg;
            if (mymsg.getClientId() != client.getClientId()){
                //System.out.println(mymsg.getName()+": "+mymsg.getMessage());
                world.addChatMessage(mymsg.getName()+": "+mymsg.getMessage());
            }
        } else if (msg instanceof LoginReplyMessage){
            LoginReplyMessage mymsg = (LoginReplyMessage)msg;
            if (mymsg.getReply()){
                world.login();
            } else {
                world.setResponse("Failed to login. Try Again?");
            }
            //world.response = true;
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
