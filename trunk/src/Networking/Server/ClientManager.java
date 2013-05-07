/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;
import GameSource.Net.Server.ServerNetListener;
import Networking.Messages.ChatMessage;
import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * @author Shiyang
 */
public class ClientManager {
    Vector clientList = null;  // This will be a list of all the clients connected
    public ClientManager() {
            clientList = new Vector();
    }

    public void addClient(ServerNetListener client) {
            clientList.add(client);  // adds a client to the list
    }

    public void removeClient(ServerNetListener client) {
            clientList.remove(client);  // removes a client from the list
    }

    // Broadcasts the message to every client connected, synchronized so that only
    // one client may send a message at a time.
    public void broadcast(ChatMessage message) {
        synchronized (clientList) {
            Enumeration e = clientList.elements();
              // loop through all the connected clients
            while (e.hasMoreElements ()) {
                //System.out.println("preparing");
                ServerNetListener client = (ServerNetListener)e.nextElement ();
                try {
                    client.send(message);  // send the message to the client
                } catch (Exception ex) {
                    client.end();
                }
            }
        }
    }    
}
