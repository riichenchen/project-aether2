/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Client;

import Networking.Messages.Message;
import Networking.Messages.RegisterClientMessage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Shiyang
 * The skeleton listener thread
 * Any implementation must override the receive message class, which deals
 * with any messages received over the network other than the reserved
 * register client message (which registers a client id with the server)
 */
public abstract class ClientListenThread extends Thread
{
    protected ObjectInputStream in = null;
    protected Socket socket = null;
    protected boolean connected = false;
    protected Client client;
    
    public ClientListenThread(Socket cSocket,Client client) {
        this.socket = cSocket;
        this.client = client;
        try {
            in = new ObjectInputStream(socket.getInputStream()); // attempt to get input stream from the socket
        } catch (Exception e){
            System.out.println("could not connect with input stream "+e.getMessage());
        }
        connected = true;
    }
    
    public abstract void ReceiveMessage(Message msg);
    
    //Returns the connection
    public boolean isConnected(){
        return connected;
    }
    
    
    public void run ()
    {
	while (true)
	{
	    try {
                Object x = in.readObject();
                if (x instanceof RegisterClientMessage) { // register this class
                    System.out.println("registered as client "+((RegisterClientMessage)x).getClientId());
                    client.setClientId(((RegisterClientMessage)x).getClientId());
                } else {
                    ReceiveMessage((Message)x); // otherwise pass the message to be dealt with
                }    
	    } catch (Exception e) {
                System.out.println("Problem Occured. Server may have died. Error:"+e.getMessage());
                e.printStackTrace();
                connected = false;
                System.exit(0);
	    }
	}
    }
}