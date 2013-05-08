/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

import Networking.Client.ClientSendThread;
import Networking.Messages.RequestLoginMessage;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public class ClientNetSender extends ClientSendThread{
    public ClientNetSender(Socket socket){
        super(socket);
    }
    @Override
    public void onConnect() {
        this.sendMessage(new RequestLoginMessage("siratori","TULIO6011996"));
        //this.sendMessage(new ClientJoinChatMessage("OKAY!"));
    }
    
}
