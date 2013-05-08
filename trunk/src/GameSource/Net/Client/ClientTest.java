/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Net.Client;

import Networking.Messages.ChatMessage;
import Networking.Messages.RequestLoginMessage;
import java.util.Scanner;

/**
 *
 * @author Shiyang
 */
public class ClientTest {
    public boolean response = false;
    public String password = "";
    public String name = "";
    public boolean loggedin = false;
    public ClientTest(){
        //MyClient c = new MyClient(this);
        //c.start();
        Scanner kb = new Scanner(System.in);
        System.out.print("Give me a UserName: ");
        name = kb.nextLine();
        System.out.print("Give me a Password: ");
        password = kb.nextLine();
        //c.sendMessage(new RequestLoginMessage(name,password));
        while (!response){}
        while (!loggedin){
            response = false;
            System.out.println("Failed to login ;C. Try again");
            System.out.print("Give me a UserName: ");
            name = kb.nextLine();
            System.out.print("Give me a Password: ");
            password = kb.nextLine();
            //c.sendMessage(new RequestLoginMessage(name,password));    
            while (!response){}
        }
        
        System.out.println("You're in the chat now, press enter to send chat.");
        
        while (true){
        //    c.sendMessage(new ChatMessage(name,kb.nextLine()));
        }
    }
    public static void main(String[] args){
        new ClientTest();
    }
        
}
