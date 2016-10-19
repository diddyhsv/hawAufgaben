package server;

import java.io.*;
import java.net.*;

public class MultiThreadServer implements Runnable {
	Socket csocket;
	static final int maxClients = 3;
	static int activeClients = 0;

	MultiThreadServer(Socket csocket) {
		this.csocket = csocket;
	}

	public static void main(String args[]) {
	try{
		ServerSocket ssock = new ServerSocket(6789);

		// System.out.println("Listening");
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
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(csocket.getOutputStream());

			try {
				while (true) {
					String clientSentence = inFromClient.readLine();
					System.out.println("Received: " + clientSentence);
					outToClient.writeBytes(clientSentence + "\n");
				}
			} catch (Exception e) {
				activeClients--;
				System.out.println(activeClients);
				Thread.currentThread().interrupt();
			}

		} catch (IOException e) {

			System.out.println(e);
		} 

	}
}