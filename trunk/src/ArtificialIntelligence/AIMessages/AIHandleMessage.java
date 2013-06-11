/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence.AIMessages;

/**
 * The AIHandle Message class takes care of all messages to the AIHandler
 * As the handler supports multi-threading, some form of synchronization
 * is necessary so that AI ticks once per game time
 * @author Shiyang
 */
public class AIHandleMessage {
    //This class simply sends action messages regarding ai calculations
    //to the main handler
    public static final int REMOVECALCULATION = 0; //Id declarations, more to be added later
    private int AIId;
    private int msgtype;
    
    public AIHandleMessage(int type,int AIId){
        this.AIId = AIId;
        this.msgtype = type;
    }
   
    public int getAIId() {
        return AIId;
    }

    public int getMsgtype() {
        return msgtype;
    }
}
