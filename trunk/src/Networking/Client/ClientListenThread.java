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
 */
public abstract class ClientListenThread extends Thread
{
    protected ObjectInputStream in = null;
    //protected ObjectOutputStream out = null;
    protected Socket socket = null;
    protected boolean connected = false;
    protected int CLIENTID;
    
    public ClientListenThread(Socket cSocket) {
        this.socket = cSocket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e){
            System.out.println("could not connect with input stream "+e.getMessage());
        }
        connected = true;
//        try {
//            out = new ObjectOutputStream(socket.getOutputStream());
//        } catch (Exception ex){
//            System.out.println("could not connect with output stream: "+ex.getMessage());
//        }
    }
    public abstract void ReceiveMessage(Message msg);
    
//    public void connect(){
//        //onConnect();
//        connected = true;
//    }
    public boolean isConnected(){
        return connected;
    }
    
    public void run ()
    {
	while (true)
	{
	    try {
                Object x = in.readObject();
                if (x instanceof RegisterClientMessage) {
                    CLIENTID = ((RegisterClientMessage)x).getClientId();
                } else {
                    ReceiveMessage((Message)x);
                }    
	    } catch (Exception e) {
                System.out.println("Problem Occured. Server may have died. Error:"+e.getMessage());
                e.printStackTrace();
                connected = false;
                System.exit(0);
	    }
	}
    }
//    public void sendMessage(Message msg){
//        try {
//            msg.setClientId(CLIENTID);
//            out.writeObject(msg);
//        } catch (Exception e){
//            System.out.println("Error sending Message: "+e.getMessage());
//        }
//    }
}