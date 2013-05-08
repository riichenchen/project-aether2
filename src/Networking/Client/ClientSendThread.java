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
 */
public abstract class ClientSendThread extends Thread
{
    //protected ObjectInputStream in = null;
    protected ObjectOutputStream out = null;
    protected Socket socket = null;
    protected boolean connected = false;
    //protected int CLIENTID;
    protected ConcurrentLinkedQueue<Object> messages = new ConcurrentLinkedQueue<>();
    protected Client client;
    
    public ClientSendThread(Socket cSocket,Client client) {
        this.socket = cSocket;
        this.client = client;
        //try {
        //    in = new ObjectInputStream(socket.getInputStream());
        //} catch (Exception e){
        //    System.out.println("could not connect with input stream "+e.getMessage());
        //}
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception ex){
            System.out.println("could not connect with output stream: "+ex.getMessage());
        }
        connected = true;
        onConnect();
    }
    public abstract void onConnect();
//    public abstract void ReceiveMessage(Message msg);
    
//    public void connect(){
//        onConnect();
//        connected = true;
//    }
    public boolean isConnected(){
        return connected;
    }
    
    @Override
    public void run ()
    {
	while (true)
	{
	    try {
                while (messages.peek() != null){
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
    public void sendMessage(Message msg){
        try {
            msg.setClientId(client.getClientId());
            out.writeObject(msg);
        } catch (Exception e){
            System.out.println("Error sending Message: "+e.getMessage());
        }
    }
}