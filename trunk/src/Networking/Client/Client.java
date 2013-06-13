/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Client;

import GameSource.Globals;
import GameSource.Net.Client.AetherClientNetListener;
import GameSource.Net.Client.AetherClientNetSender;
import Networking.Messages.Message;
import java.net.Socket;

/**
 *
 * @author Shiyang
 * The skeleton network client class.
 * Provides a connection using Sockets with TCP.
 * Also binds a listener and sender class on separate threads
 */
public abstract class Client
{
    protected static Socket csocket = null;
    protected boolean started = false;
    protected ClientListenThread netlistener = null;
    protected ClientSendThread netSender = null;
    protected int clientId;
    
    public Client(){
        try {
            csocket = new Socket (Globals.__IP__, 4186); //request a connection from a given ip on port 4186
            csocket.setTcpNoDelay(true);
            System.out.println ("Connected to host" + csocket.getRemoteSocketAddress ());
            netlistener = new AetherClientNetListener(csocket,this); // start a listener and sender
            netSender = new AetherClientNetSender(csocket,this);
        } catch (Exception e){
	    System.out.println ("Unable to connect to host with localhost");
            System.exit(0);
	}
    }
    
    //starts the client and listener
    public void start(){
        netlistener.start();
        netSender.start();
        started = true;
    }
    //sends a message to the server
    public void sendMessage(Message msg){
        if (!started){
            System.out.println("Client not started!");
            return;
        }
        netSender.sendMessage(msg);
    }
    
    //standard set and get methods
    
    public ClientListenThread getClientNetListener(){
        return netlistener;
    }
    public void setClientId(int id){
        this.clientId = id;
    }
    public int getClientId(){
        return clientId;
    }
} 
