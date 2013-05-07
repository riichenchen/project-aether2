/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

import Networking.Messages.ChatMessage;
import java.util.Scanner;

/**
 *
 * @author Shiyang
 */
public class ClientTest {
    public static void main(String[] args){
        Scanner kb = new Scanner(System.in);
        String name = "Anonymous name error";
        
        System.out.print("Give me a UserName: ");
        name = kb.nextLine();
        
        MyClient c = new MyClient(name);
        c.start();
        
        while (true){
            c.sendMessage(new ChatMessage(name,kb.nextLine()));
        }
    }
}
