/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Client;

import GameSource.Net.Client.ClientNetListener;
import Networking.Globals;
import Networking.Messages.Message;
import java.net.Socket;

/**
 *
 * @author Shiyang
 */
public abstract class Client
{
    protected static Socket csocket = null;
    //private ObjectOutputStream out = null;
    protected boolean started = false;
    protected ClientNetListener t = null;
    public Client (){} 
    
    public void start(){
	try {
            csocket = new Socket (Globals.__IP__, 4186);
	    System.out.println ("Connected to host" + csocket.getRemoteSocketAddress ());
	    //out = new ObjectOutputStream(csocket.getOutputStream ());
            t = new ClientNetListener(csocket);
            initListener();
            t.connect();
            t.start();
            started = true;
	} catch (Exception e){
	    System.out.println ("Unable to connect to host with localhost");
            System.exit(0);
	}
    }
    public void sendMessage(Message msg){
        if (!started){
            System.out.println("Client not started!");
            return;
        }
        t.sendMessage(msg);
    }
    
    public ClientNetListener getClientNetListener(){
        return t;
    }
    
    public abstract void initListener();
} // ChatClient class
