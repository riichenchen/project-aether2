/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.Serializable;

/**
 * The login reply class is sent to the client
 * containing information about acceptance or rejection
 * and a message why if rejection occurred
 * @author Shiyang
 */
public class LoginReply implements Serializable{
    private boolean accepted;
    private int accId;
    private String message;
    
    public LoginReply(int accId,boolean accepted,String message) {
        this.accId = accId;
        this.accepted = accepted;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public int getAccId() {
        return accId;
    }
    
}
