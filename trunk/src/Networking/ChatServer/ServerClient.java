/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.ChatServer;

import Networking.Messages.ChatMessage;
import Networking.Messages.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public class ServerClient extends Thread{
	private Socket cSocket = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private ChatManager manager = null;
	private boolean stayConnected = true;

	public ServerClient(Socket cSocket, ChatManager manager) {
		this.cSocket = cSocket;
		this.manager = manager;
		try {
			in = new ObjectInputStream(cSocket.getInputStream());
		} catch (Exception e) {}
		try {
			out = new ObjectOutputStream(cSocket.getOutputStream());
		} catch (Exception e) {}
		manager.addClient(this);  // get added to the list of clients
	}
	
	public void run() {
		try {
			while(stayConnected) {
				Object msg = in.readObject();
				if (msg instanceof ChatMessage){
                                    ChatMessage mymsg = (ChatMessage)msg;
                                    if(mymsg.getMessage().equalsIgnoreCase("quit")) {
                                        stayConnected = false;
                                    } else {
                                        manager.broadcast(mymsg);
                                    }
				}
			}
		} catch (Exception e) {
			
		} finally {
			manager.removeClient(this);
			try {
				in.close();
				out.close();
				cSocket.close();
			} catch (Exception e){}
			manager = null;
		}
	}
	
	public synchronized void send(Message message) throws IOException{
            //System.out.println("message sent");
            out.writeObject(message);
		//System.out.println(message.getMessage());
		//out.newLine();
		//out.flush();
	}
	
	public void end() {
		stayConnected = false;
	}
}

