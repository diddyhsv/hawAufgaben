package server;

import java.io.*;
import java.net.*;

class TCPServer extends Thread
{

@Override
public void run(){
	try {
		 String clientSentence;
	     String capitalizedSentence;
	     ServerSocket welcomeSocket = new ServerSocket(6789);
	     Socket connectionSocket = welcomeSocket.accept();

		while(true)
			
	    {
			
	    BufferedReader inFromClient =
	       new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
	    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	    clientSentence = inFromClient.readLine();
	    System.out.println("Received: " + clientSentence);
	    capitalizedSentence = clientSentence + '\n';
	    outToClient.writeBytes(capitalizedSentence);
	 }		
	} catch (Exception e) {
		System.out.println("Thread throws exception: " + e);
	}}
   
   
}