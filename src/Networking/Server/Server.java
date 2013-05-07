/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;

import GameSource.Net.Server.ServerMessageListener;
import Networking.Messages.Message;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Shiyang
 */
public class Server extends Thread{
    private ClientManager manager;
    private boolean acceptConns = true;
    private ServerSocket sSocket = null;
    private Socket cSocket = null;
    
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
    }

    public void run(){
        System.out.println("Waiting on Connections");
        while(acceptConns) {
            try {
                // accept a connection from a client
                cSocket = sSocket.accept();
                System.out.println("Client Connected: " + cSocket.getInetAddress().getHostAddress());
                // Start a thread to handle each client, the client will add itself to the managers list
                ServerMessageListener client = new ServerMessageListener(cSocket, manager);
                //client.send(new ServerHandshakeMessage(Globals.__SERVERVERSION__));
                client.start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
