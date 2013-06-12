/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

import GameSource.Game.ClientWorldHandler;
import Networking.Client.Client;

/**
 * @author Shiyang
 * Example implementation of a Client
 * Make sure the client has a handle the world
 */
public class AetherClient extends Client{
    ClientWorldHandler world;
    public AetherClient(ClientWorldHandler world){
        super();
        this.world = world; //set the world, bind the net sender to the world
        world.bindSender((AetherClientNetSender)netSender);
        ((AetherClientNetListener)netlistener).setWorld(world);
    }
}
