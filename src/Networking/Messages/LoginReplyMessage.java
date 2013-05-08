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
    public LoginReplyMessage(boolean b){
        ans = b;
    }
    public boolean getReply(){
        return ans;
    }
}
