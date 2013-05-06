/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.ChatServer;

import Networking.Globals;
import Networking.Messages.ServerHandshakeMessage;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public class ChatServer {
	public ChatServer() {
	}

	public static void main(String[] args) {
		ServerSocket sSocket = null;
		boolean acceptConns = true;
		Socket cSocket = null;
		try {
			// Start the server on port 5000, print out some information about the server
			sSocket = new ServerSocket(5000);
			System.out.println("Server Started");
			InetAddress inet = InetAddress.getLocalHost();
			System.out.println("Host Name: " + inet.getHostName());
			System.out.println("IP Address: " + inet.getHostAddress());
			System.out.println("Port: " + sSocket.getLocalPort());
		} catch (Exception e) {
			System.err.println("Could not start server on port 5000");
			acceptConns = false;
		}
		// The manager will keep track of all clients connected to the server
		ChatManager manager = new ChatManager();
		
		while(acceptConns) {
			try {
				// accept a connection from a client
				cSocket = sSocket.accept();
				System.out.println("Client Connected: " + cSocket.getInetAddress().getHostAddress());
				// Start a thread to handle each client, the client will add itself to the managers list
				ServerClient client = new ServerClient(cSocket, manager);
                                //client.send(new ServerHandshakeMessage(Globals.__SERVERVERSION__));
				client.start();
                                
			} catch (Exception e) {
				System.out.println("Client failed to connect");
			}
			
			
		}
	}
	
	
}
