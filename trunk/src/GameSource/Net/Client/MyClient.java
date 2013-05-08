/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

import Networking.Client.Client;

/**
 *
 * @author Shiyang
 */
public class MyClient extends Client{
    private String clientId;
    public MyClient(String clientId){
        this.clientId = clientId;
    }

    @Override
    public void initListener() {
       t.setUserID(clientId);
    }
    
}
