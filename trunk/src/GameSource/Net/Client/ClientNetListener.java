/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

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
    //private String userid;
    
    public ClientNetListener(Socket csocket){
        super(csocket);
    }
    

    @Override
    public void ReceiveMessage(Message msg) {
        if (msg instanceof ChatMessage){
            ChatMessage mymsg = (ChatMessage)msg;
            if (mymsg.getClientId() != this.CLIENTID){
                System.out.println(mymsg.getName()+": "+mymsg.getMessage());
            }
        } else if (msg instanceof LoginReplyMessage){
            LoginReplyMessage mymsg = (LoginReplyMessage)msg;
            if (mymsg.getReply()){
                System.out.println("Accepted!");
            } else {
                System.out.println("Declined!");
            }
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
