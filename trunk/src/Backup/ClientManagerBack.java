/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Backup;

/**
 *
 * @author Shiyang
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import GameSource.Net.Server.ServerNetListener;
import Networking.Messages.ChatMessage;
import Networking.Messages.Message;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Shiyang
 */
public class ClientManagerBack {
    private ConcurrentHashMap<Integer,ServerNetListener> clientList = null;  // This will be a list of all the clients connected
    private int numberClients = 0;
    public ClientManagerBack() {
            clientList = new ConcurrentHashMap<Integer,ServerNetListener>();
    }

    public void addClient(ServerNetListener client) {
            clientList.put(numberClients, client);  // adds a client to the list
            client.setConnectionId(numberClients++);
    }

    public void removeClient(ServerNetListener client) {
            clientList.remove(client.getConnectionId());  // removes a client from the list
    }

    // Broadcasts the message to every client connected, synchronized so that only
    // one client may send a message at a time.
    public synchronized void broadcast(ChatMessage message) {
        Iterator e = clientList.entrySet().iterator();
          // loop through all the connected clients
        while (e.hasNext()) {
            ServerNetListener client = (ServerNetListener)e.next();
            try {
                client.send(message);  // send the message to the client
            } catch (Exception ex) {
                client.end();
            }
        }
    }
    
    public void sendToClient(int clientId, Message m){
        ServerNetListener myclient = clientList.get(clientId);
        if (myclient == null){
            System.out.println("ERROR UNABLE TO FIND CLIENT WITH ID "+clientId);
            return;
        }
        myclient.send(m);
    }
}
