/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;
//import GameSource.Net.Server.ServerNetListener;
import Networking.Messages.ChatMessage;
import Networking.Messages.Message;
import Networking.Messages.RegisterClientMessage;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Shiyang
 */
public class ClientManager {
    private ConcurrentHashMap<Integer,Object> clientList = new ConcurrentHashMap<>();  // This will be a list of all the clients connected
    private int numClients = 0;
    public ClientManager() {}

    public void addClient(ServerClient client) {
        client.setConnectionId(numClients);
        clientList.put(numClients,client);  // adds a client to the list
        client.send(new RegisterClientMessage(numClients++));
    }

    public void removeClient(ServerClient client) {
        clientList.remove(client.getConnectionId());  // removes a client from the list
        System.out.println("Client "+client.getConnectionId()+" has disconnected.");
        broadcast(new ChatMessage("Server","Client "+client.getConnectionId()+" has disconnected."));
    }

    // Broadcasts the message to every client connected, synchronized so that only
    // one client may send a message at a time.
    public void broadcast(Message message) {
        synchronized (clientList) {
            Enumeration e = clientList.elements();
              // loop through all the connected clients
            while (e.hasMoreElements ()) {
                //System.out.println("preparing");
                ServerClient client = (ServerClient)e.nextElement ();
                try {
                    client.send(message);  // send the message to the client
                } catch (Exception ex) {
                    client.end();
                }
            }
        }
    }
    
    public void sendToOne(int clientId, Message message){
        try {
            ((ServerClient)clientList.get(clientId)).send(message);
        } catch (Exception e){
            System.out.println("Unable to send to client id: "+clientId+". Error: "+e.getMessage());
            e.printStackTrace();
        }
    }
}