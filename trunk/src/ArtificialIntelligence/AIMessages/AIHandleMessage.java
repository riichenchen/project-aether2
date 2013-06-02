/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence.AIMessages;

/**
 *
 * @author Shiyang
 */
public class AIHandleMessage {
    public static final int REMOVECALCULATION = 0;
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
