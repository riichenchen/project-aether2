/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Server;

import Networking.Server.ClientManager;
import Networking.Server.Server;
import Networking.Server.ServerClient;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public class MyServer extends Server{
    //ServerTest world;
    public MyServer(){
        super();
    //    this.world = world;
    }

    @Override
    public ServerClient addListener(Socket cSocket,ClientManager manager) {
        return (ServerClient) (new ServerNetListener(cSocket,manager));//,world));
    }
}
