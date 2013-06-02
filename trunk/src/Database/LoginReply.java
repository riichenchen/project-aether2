/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author Shiyang
 */
public class LoginReply {
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
