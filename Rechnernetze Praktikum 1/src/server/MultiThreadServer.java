package server;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiThreadServer implements Runnable {
	Socket csocket;
	static final int maxClients = 3;
	static int activeClients = 0;
	static String password;

	MultiThreadServer(Socket csocket) {
		this.csocket = csocket;
	}

	public static void main(String args[]) {
	try{
		ServerSocket ssock = new ServerSocket(6789);
		password = args[0];
		//System.out.println(password);
		while (true) {
			System.out.println("");
			
			if (activeClients < maxClients) {
		//		System.out.println("Challenge");

				Socket sock = ssock.accept();
			//	System.out.println("accepted");
				new Thread(new MultiThreadServer(sock)).start();
				activeClients++;
			}
			
		}
		
		}catch(Exception e){
			System.out.println("Fehler in main");
		}
	}

	public void run() {
		try {
			if(activeClients<=maxClients){
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(csocket.getOutputStream());

			try {
				while (true) {
					String clientSentence = inFromClient.readLine();
					System.out.println("Received: " + clientSentence);
					if(clientSentence.matches("UPPERCASE.*")){
						if(clientSentence.replaceFirst("UPPERCASE", "").matches("")){
							outToClient.writeBytes("ERROR SYNTAX ERROR EMPTY STRING NOT ALLOWED" + "\n");}
						else
						outToClient.writeBytes(clientSentence.replaceFirst("UPPERCASE", "ok").toUpperCase() + "\n");		
					}
					else if(clientSentence.matches("LOWERCASE.*")){
						if(clientSentence.replaceFirst("LOWERCASE", "").matches("")){
							outToClient.writeBytes("ERROR SYNTAX ERROR EMPTY STRING NOT ALLOWED" + "\n");}
						else
						outToClient.writeBytes("OK" + clientSentence.replaceFirst("LOWERCASE", "").toLowerCase() + "\n");
					}
					else if(clientSentence.matches("REVERSE.*")){
						if(clientSentence.replaceFirst("REVERSE", "").matches("")){
							outToClient.writeBytes("ERROR SYNTAX ERROR EMPTY STRING NOT ALLOWED" + "\n");}
						else{
						clientSentence = clientSentence.replaceFirst("REVERSE", "");
						String reverse = new StringBuffer(clientSentence).reverse().toString();
						outToClient.writeBytes("OK " + reverse + "\n");
					}}
					else if(clientSentence.matches("BYE.*")){
						if(clientSentence.matches("BYE")){
							outToClient.writeBytes("OK BYE" + "\n");
							throw new Exception();}
						else{
							outToClient.writeBytes("ERROR SYNTAX ERROR NO PARAMETERS ALLOWED" + "\n");}
						
						
					}
					else if(clientSentence.matches("SHUTDOWN.*")){
						if (clientSentence.replaceFirst("SHUTDOWN ", "").matches(password)){
							activeClients = maxClients*3;
							outToClient.writeBytes("OK SHUTDOWN" + "\n");
							throw new Exception();
						}
						else{
							outToClient.writeBytes("ERROR SYNTAX ERROR WRONG PASSWORD" + "\n");
						}
					}
					else{
					outToClient.writeBytes("ERROR UNKNOWN COMMAND" + "\n");
				}}
			} catch (Exception e) {
				activeClients--;
				System.out.println(activeClients);
				Thread.currentThread().interrupt();
			}}

		} catch (IOException e) {

			System.out.println(e);
		} 

	}
}