/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;
import Networking.Messages.Message;
import Networking.Messages.RegisterClientMessage;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Shiyang
 * The ClientManager class manages all clients that are currently connected to the
 * server and provides a way for clients to message each other as well as a way
 * for the server to message each of its clients individually.
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
    }

    // Broadcasts the message to every client connected, synchronized so that only
    // one client may send a message at a time.
    public void broadcast(Message message) {
        synchronized (clientList) {
            Enumeration e = clientList.elements();
              // loop through all the connected clients
            while (e.hasMoreElements ()) {
                ServerClient client = (ServerClient)e.nextElement ();
                try {
                    client.send(message);  // send the message to the client
                } catch (Exception ex) {
                    client.end();
                }
            }
        }
    }
    
    //Takes a message and sends it to the client with the provided id.
    public void sendToOne(int clientId, Message message){
        try {
            ((ServerClient)clientList.get(clientId)).send(message);
        } catch (Exception e){
            //Warns if the id doesn't exist or an error occurs
            System.out.println("Unable to send to client id: "+clientId+". Error: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
