/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.ChatClient;

import Networking.Messages.ChatMessage;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public class ListenThread extends Thread
{
    ObjectInputStream in = null;
    Socket socket = null;
    private boolean running = true;
    
    public ListenThread(Socket cSocket) {
        socket = cSocket;
        try {
        in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception ex){
            System.out.println("error: "+ex.getMessage());
        }
        
    }
    public boolean isRunning(){
        return running;
    }
    public void run ()
    {
	while (true)
	{
	    try
	    {
                //System.out.println("Listening:");
                
                Object x = in.readObject();
                if (x instanceof ChatMessage){
                    ChatMessage incomming = (ChatMessage)x;
                        System.out.println(incomming.getName()+": "+incomming.getMessage());
                }
	    }
	    catch (Exception e)
	    {
                System.out.println("Problem Occured. Server may have died. Error:"+e.getMessage());
                running = false;
                System.exit(0);
	    }
	}
    }
}