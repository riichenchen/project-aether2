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
 * @author Shiyang
 * The server of the game. Binds to the world handler.
 */
public class AetherServer extends Server{
    ServerWorldHandler world;
    public AetherServer(ServerWorldHandler world){
        super();
        this.world = world;
        world.bindManager(manager);
        ((AetherNetListener)netListener).setWorld(world);//bind to world
    }
    
    @Override//return aethernetlistener as the servernetlistener
    public ServerNetListener addListener(ClientManager manager) {
        return (ServerNetListener)(new AetherNetListener(manager));
    }
    
}
