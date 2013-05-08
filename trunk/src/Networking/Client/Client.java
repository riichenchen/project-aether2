/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Client;

import GameSource.Net.Client.ClientNetListener;
import GameSource.Net.Client.ClientNetSender;
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
    protected ClientNetListener netlistener = null;
    protected ClientSendThread netSender = null;
    
    public Client (){} 
    
    public void start(){
	try {
            csocket = new Socket (Globals.__IP__, 4186);
	    System.out.println ("Connected to host" + csocket.getRemoteSocketAddress ());
            netlistener = new ClientNetListener(csocket);
            //initListener();
            netlistener.start();
            netSender = new ClientNetSender(csocket);
            //netSender.connect();
            netSender.start();
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
        netSender.sendMessage(msg);
    }
    
    public ClientNetListener getClientNetListener(){
        return netlistener;
    }
    
    //public abstract void initListener();
} // ChatClient class
