/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;

import Networking.Messages.Message;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Shiyang
 * The ServerNetListener class handles all incoming messages from
 * the manager
 */
public class ServerNetListener extends Thread{
    protected ClientManager manager = null;
    protected boolean stayConnected = true;
    protected ConcurrentLinkedQueue<Message> messages = new ConcurrentLinkedQueue<>();
    
    public ServerNetListener(ClientManager manager) {
        this.manager = manager;
    }

    public synchronized void addMessage(Message m){
        messages.add(m);
    }
    public void ReceiveMessage(Message m){
        System.out.println("Message Received!");    
        //Note: the code here is overridden by the implementation, aethernetlistener
    }
    
    @Override
    public void run() {
        while (true){ // continue to plough through all received messages
            while (messages.peek()!= null){
                ReceiveMessage(messages.poll());
            }
        }
    }
}

