/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;

//import Networking.Server.ServerNetListener;
import GameSource.Globals;
import Networking.Messages.Message;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Shiyang
 * The skeleton class for a server.
 * Manages a socket connection as well as a listener and manager
 */
public abstract class Server extends Thread{
    protected ClientManager manager;
    protected boolean acceptConns = true;
    protected ServerSocket sSocket = null;
    protected Socket cSocket = null;
    protected ServerNetListener netListener;
            
    public Server() {
            try {
                // Start the server, print out some information about the server
                sSocket = new ServerSocket(Globals.__PORT__);
                InetAddress inet = InetAddress.getLocalHost();
                System.out.println("Host Name: " + inet.getHostName());
                System.out.println("IP Address: " + inet.getHostAddress());
                System.out.println("Port: " + sSocket.getLocalPort());
            } catch (Exception e) {
                System.err.println("Could not start server on port 4186");
                acceptConns = false;
            }
            // The manager will keep track of all clients connected to the server
            manager = new ClientManager();
            netListener = addListener(manager);
            netListener.start();
    }
    //Should a message be received, multiple listener threads may add a message
    //at the same time. Because of this, synchronization is necessary
    public synchronized void receiveMessage(Message m){
        netListener.addMessage(m);
    }
    
    public void run(){
        System.out.println("Waiting on Connections");
        while(acceptConns) {//listening for connections at all times
            try {
                // accept a connection from a client
                cSocket = sSocket.accept();
                cSocket.setTcpNoDelay(true);
                System.out.println("Client Connected: " + cSocket.getInetAddress().getHostAddress());
                // Start a thread to handle each client, the client will add itself to the managers list
                ServerClient client = new ServerClient(cSocket, manager,this);
                client.start();
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    //Called to add the manager
    public abstract ServerNetListener addListener(ClientManager manager);
}
