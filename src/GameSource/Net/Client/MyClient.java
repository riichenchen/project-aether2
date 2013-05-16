/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

import GameSource.LoginFrame;
import Networking.Client.Client;

/**
 *
 * @author Shiyang
 */
public class MyClient extends Client{
//    private String clientId;
    public MyClient(LoginFrame world){
        super(world);
        ((ClientNetListener)netlistener).setWorld(world);
//        this.clientId = clientId;
    }

//    @Override
//    public void initListener() {
////       netlistener.setUserID(clientId);
//    }
    
}
