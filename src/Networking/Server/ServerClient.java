/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;

import GameSource.Net.Server.ServerNetListener;
import Networking.Messages.ChatMessage;
import Networking.Messages.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Shiyang
 */
public abstract class ServerClient extends Thread{
    protected Socket cSocket = null;
    protected ObjectInputStream in = null;
    protected ObjectOutputStream out = null;
    protected ClientManager manager = null;
    protected boolean stayConnected = true;

    public ServerClient(Socket cSocket, ClientManager manager) {
        this.cSocket = cSocket;
        this.manager = manager;
        try {
            out = new ObjectOutputStream(cSocket.getOutputStream());
        } catch (Exception e) {}
        try {
            in = new ObjectInputStream(cSocket.getInputStream());
        } catch (Exception e) {
            
        } 
        manager.addClient((ServerNetListener)this);  // get added to the list of clients
    }
    public abstract void ReceiveMessage(Message m);
    
    public void run() {
        try {
            while(stayConnected) {
                Object msg = in.readObject();
                ReceiveMessage((Message)msg);
            }
        } catch (Exception e) {

        } finally {
            manager.removeClient((ServerNetListener)this);
            try {
                in.close();
                out.close();
                cSocket.close();
            } catch (Exception e){}
            manager = null;
        }
    }

    public synchronized void send(Message message) throws IOException{
        out.writeObject(message);
    }

    public void end() {
        stayConnected = false;
    }
}

