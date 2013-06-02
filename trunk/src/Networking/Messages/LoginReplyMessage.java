/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Messages;

import java.io.Serializable;

/**
 *
 * @author Shiyang
 */
public class LoginReplyMessage extends Message implements Serializable {
    public LoginReplyMessage(){}
    private boolean ans;
    private String message;
    public LoginReplyMessage(boolean b,String message){
        this.ans = b;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public boolean getReply(){
        return ans;
    }
}
