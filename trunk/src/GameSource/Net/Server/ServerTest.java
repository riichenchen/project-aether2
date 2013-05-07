/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Server;

import Networking.Server.Server;

/**
 *
 * @author Shiyang
 */
public class ServerTest {
    public static void main(String[] args){
        Server myserver = new Server();
        myserver.start();
    }
}
