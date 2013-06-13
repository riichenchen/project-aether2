/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;

//import GameSource.Net.Server.ServerNetListener;
import Networking.Messages.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Shiyang
 * The serverclient class manages all connections to a specific client
 * socket.
 */
public class ServerClient extends Thread{
    protected Socket cSocket = null;
    protected ObjectInputStream in = null;
    protected ObjectOutputStream out = null;
    protected ClientManager manager = null;
    protected boolean stayConnected = true;
    protected int connectionId;
    protected Server theServer;
    
    public ServerClient(Socket cSocket, ClientManager manager,Server theServer) {
        this.cSocket = cSocket;
        this.manager = manager;
        this.theServer = theServer;
        // attempt to connect with input and output stream from sockets
        try {
            out = new ObjectOutputStream(cSocket.getOutputStream()); 
        } catch (Exception e) {}
        try {
            in = new ObjectInputStream(cSocket.getInputStream());
        } catch (Exception e) {
            
        } 
        manager.addClient((ServerClient)this);  // get added to the list of clients
    }
    public void setConnectionId(int id){
        connectionId = id;
    }
    public int getConnectionId(){
        return connectionId;
    }
    
    
    @Override
    public void run() {
        try {
            while(stayConnected) { // continue to wait for messages from this client
                Object msg = in.readObject();
                theServer.receiveMessage((Message)msg);
            }
        } catch (Exception e) {

        } finally {
            //should they disconnnect, remove them from the manager
            manager.removeClient(this);
            try {
                in.close();
                out.close();
                cSocket.close();
            } catch (Exception e){
                System.out.println("SEVERE: Unable to close client! D:");
                e.printStackTrace();
            }
            manager = null;
        }
    }
    
    //The send method takes a message and ships it off to the corresponding client
    public synchronized void send(Message message){
        try {
            out.writeObject(message);
        } catch (IOException e){
            System.out.println("Unable to send message!");
        }
    }

    public void end() {
        stayConnected = false;
    }
}

