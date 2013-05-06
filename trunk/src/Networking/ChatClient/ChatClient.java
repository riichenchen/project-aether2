/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.ChatClient;

import Networking.Messages.ChatMessage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Shiyang
 */
public class ChatClient
{
    public static Socket csocket = null;
    public static void main (String[] args)
    {

	int digit = 98;
	ObjectOutputStream out = null;
	String str, username = "-user-name-error-";
	int found = 0;
        ListenThread t = null;
	Scanner stdin = new Scanner(System.in);
	try
	{
	    csocket = new Socket ("24.57.115.97", 4186);
	    System.out.println ("Connected to host" + csocket.getRemoteSocketAddress ());
            System.out.println("Please give me a username:");
            username = stdin.nextLine();
	    out = new ObjectOutputStream(csocket.getOutputStream ());
            t = new ListenThread (csocket);
            t.start ();
	}
	catch (Exception e)
	{
	    System.out.println ("Unable to connect to host with localhost" + digit);
            System.exit(0);
	}

	while (((ListenThread)t).isRunning())
	{
	    try
	    {
			str = stdin.nextLine (); // gets the input from the user
			out.writeObject (new ChatMessage(username,str));
	    }
	    catch (Exception e)
	    {
	    }
	}
    } // main method
} // ChatClient class
