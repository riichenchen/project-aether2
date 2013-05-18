/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;

//import GameSource.Net.Server.ServerNetListener;
import Networking.Messages.Message;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Shiyang
 */
public class ServerNetListener extends Thread{
//    protected Socket cSocket = null;
//    protected ObjectInputStream in = null;
//    protected ObjectOutputStream out = null;
    protected ClientManager manager = null;
    protected boolean stayConnected = true;
//    protected int connectionId;
    protected ConcurrentLinkedQueue<Message> messages = new ConcurrentLinkedQueue<>();
    
    public ServerNetListener(ClientManager manager) {
        this.manager = manager;
    }
    
//    public void setConnectionId(int id){
//        connectionId = id;
//    }
//    public int getConnectionId(){
//        return connectionId;
//    }
    public synchronized void addMessage(Message m){
        messages.add(m);
        //System.out.println(messages.peek());
    }
    public void ReceiveMessage(Message m){
        System.out.println("Message Received!");    
        //Note: the code here is overridden by the implementation, aethernetlistener
    }
    
    @Override
    public void run() {
        while (true){
            while (messages.peek()!= null){
                ReceiveMessage(messages.poll());
            }
        }
//        try {
//            while(stayConnected) {
//                Object msg = in.readObject();
//                ReceiveMessage((Message)msg);
//            }
//        } catch (Exception e) {
//
//        } finally {
//            manager.removeClient((ServerNetListener)this);
//            try {
//                in.close();
//                out.close();
//                cSocket.close();
//            } catch (Exception e){}
//            manager = null;
//        }
    }

//    public synchronized void send(Message message){
//        try {
//            //message.setClientId(-1);
//            out.writeObject(message);
//        } catch (IOException e){
//            System.out.println("Unable to send message!");
//        }
//    }

//    public void end() {
//        stayConnected = false;
//    }
}

