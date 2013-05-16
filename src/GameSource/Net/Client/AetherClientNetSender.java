/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

import Networking.Client.Client;
import Networking.Client.ClientSendThread;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public class AetherClientNetSender extends ClientSendThread{
    public AetherClientNetSender(Socket socket,Client client){
        super(socket,client);
    }
    @Override
    public void onConnect() {
        //this.sendMessage(new RequestLoginMessage("siratori","TULIO6011996"));
        //this.sendMessage(new ClientJoinChatMessage("OKAY!"));
    }
    
}
