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
public class ChatMessage extends Message implements Serializable {
	public ChatMessage(){}
	private String message;
        private String name;
	public ChatMessage(String name,String message){
            this.message = message;
            this.name = name;
	}
	public String getMessage(){
            return message;
	}
        public String getName(){
            return name;
        }
}