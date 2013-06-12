/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

import Networking.Client.Client;
import Networking.Client.ClientSendThread;
import java.net.Socket;

/**
 * @author Shiyang
 * The aether sender.
 * No special properties.
 * May add a version control later
 */
public class AetherClientNetSender extends ClientSendThread{
    public AetherClientNetSender(Socket socket,Client client){
        super(socket,client);
    }
    @Override
    public void onConnect() {
    }
    
}
