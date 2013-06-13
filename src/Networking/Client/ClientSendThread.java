/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Client;

import Networking.Messages.Message;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Shiyang
 * The skeleton clientsendthread.
 * All implementations must override an onConnect method 
 * which is the first action done when this class attempts to connect
 * with the server.
 */
public abstract class ClientSendThread extends Thread {
    protected ObjectOutputStream out = null;
    protected Socket socket = null;
    protected boolean connected = false;
    //concurrency is necessary as multiple threads may be pulling or adding to the messages
    //while they're being parsed
    protected ConcurrentLinkedQueue<Object> messages = new ConcurrentLinkedQueue<>();
    protected Client client;
    
    public ClientSendThread(Socket cSocket,Client client) {
        this.socket = cSocket;
        this.client = client;
        try {
            out = new ObjectOutputStream(socket.getOutputStream()); //attempt to grab output stream from socket
        } catch (Exception ex){
            System.out.println("could not connect with output stream: "+ex.getMessage());
        }
        connected = true;
        onConnect();
    }
    
    public abstract void onConnect();
    
    //Returns connection
    public boolean isConnected(){
        return connected;
    }
    
    @Override
    public void run () {
	while (true) {
	    try {
                while (messages.peek() != null){ // send any messages that are queued up
                    sendMessage((Message)messages.poll());
                }
	    } catch (Exception e) {
                System.out.println("Problem Occured. Server may have died. Error:"+e.getMessage());
                e.printStackTrace();
                connected = false;
                System.exit(0);
	    }
	}
    }
    //The send message method actually packages the message and sends it on its
    //way.
    public void sendMessage(Message msg){
        try {
            msg.setClientId(client.getClientId());
            out.writeObject(msg);
        } catch (Exception e){
            System.out.println("Error sending Message: "+e.getMessage());
        }
    }
}