/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;

//import GameSource.Net.Server.ServerNetListener;
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
    protected Socket cSocket = null;
    protected ObjectInputStream in = null;
    protected ObjectOutputStream out = null;
    protected ClientManager manager = null;
    protected boolean stayConnected = true;
    protected int connectionId;
    protected Server theServer;
    
    public ServerClient(Socket cSocket, ClientManager manager,Server theServer) {
        this.cSocket = cSocket;
        this.manager = manager;
        this.theServer = theServer;
        try {
            out = new ObjectOutputStream(cSocket.getOutputStream());
        } catch (Exception e) {}
        try {
            in = new ObjectInputStream(cSocket.getInputStream());
        } catch (Exception e) {
            
        } 
        manager.addClient((ServerClient)this);  // get added to the list of clients
    }
    public void setConnectionId(int id){
        connectionId = id;
    }
    public int getConnectionId(){
        return connectionId;
    }
    
    //public abstract void ReceiveMessage(Message m);
    
    @Override
    public void run() {
        try {
            while(stayConnected) {
                Object msg = in.readObject();
                theServer.receiveMessage((Message)msg);
            }
        } catch (Exception e) {

        } finally {
            manager.removeClient(this);
            try {
                in.close();
                out.close();
                cSocket.close();
            } catch (Exception e){
                System.out.println("SEVERE: Unable to close client! D:");
                e.printStackTrace();
            }
            manager = null;
        }
    }

    public synchronized void send(Message message){
        try {
            //message.setClientId(-1);
            out.writeObject(message);
        } catch (IOException e){
            System.out.println("Unable to send message!");
        }
    }

    public void end() {
        stayConnected = false;
    }
}

