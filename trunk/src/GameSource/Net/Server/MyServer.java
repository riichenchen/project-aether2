/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Server;

import Networking.Server.ClientManager;
import Networking.Server.Server;
import Networking.Server.ServerNetListener;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public class MyServer extends Server{
    private ServerTest world;
    
    public MyServer(ServerTest world){
        super();
        this.world = world;
        ((AetherNetListener)netListener).setWorld(world);
        //System.out.println(world);
        //setListener();
    }

    @Override
    public ServerNetListener addListener(ClientManager manager) {
        return (ServerNetListener) (new AetherNetListener(manager));//,world));
    }
}
