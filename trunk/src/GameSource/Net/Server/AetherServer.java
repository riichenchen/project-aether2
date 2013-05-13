/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Server;

import GameSource.Game.ServerWorldHandler;
import Networking.Server.ClientManager;
import Networking.Server.Server;
import Networking.Server.ServerNetListener;

/**
 *
 * @author Shiyang
 */
public class AetherServer extends Server{
    ServerWorldHandler world;
    public AetherServer(ServerWorldHandler world){
        super();
        this.world = world;
        ((AetherNetListener)netListener).setWorld(world);
    }
    @Override
    public ServerNetListener addListener(ClientManager manager) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
