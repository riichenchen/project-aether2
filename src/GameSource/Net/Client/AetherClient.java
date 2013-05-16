/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

import GameSource.Game.ClientWorldHandler;
import Networking.Client.Client;

/**
 *
 * @author Shiyang
 */
public class AetherClient extends Client{
    ClientWorldHandler world;
    public AetherClient(ClientWorldHandler world){
        super();
        this.world = world;
        world.bindSender((AetherClientNetSender)netSender);
        //System.out.println(netlistener);
        ((AetherClientNetListener)netlistener).setWorld(world);
    }
}
