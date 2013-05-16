/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;

//import Networking.Server.ServerNetListener;
import Networking.Messages.Message;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public abstract class Server extends Thread{
    protected ClientManager manager;
    protected boolean acceptConns = true;
    protected ServerSocket sSocket = null;
    protected Socket cSocket = null;
    protected ServerNetListener netListener;
            
    public Server() {
            try {
                // Start the server on port 5000, print out some information about the server
                sSocket = new ServerSocket(4186);
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
    public void receiveMessage(Message m){
        netListener.addMessage(m);
    }
    public void run(){
        System.out.println("Waiting on Connections");
        while(acceptConns) {
            try {
                // accept a connection from a client
                cSocket = sSocket.accept();
                System.out.println("Client Connected: " + cSocket.getInetAddress().getHostAddress());
                // Start a thread to handle each client, the client will add itself to the managers list
                //addListener(manager).start();
                ServerClient client = new ServerClient(cSocket, manager,this);
                client.start();
                //client.send(new ServerHandshakeMessage(Globals.__SERVERVERSION__));
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public abstract ServerNetListener addListener(ClientManager manager);
}
