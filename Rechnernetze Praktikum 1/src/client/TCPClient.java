package client;

import java.io.*;
import java.net.*;

class TCPClient
{
 public static void main(String argv[]) throws Exception
 {
	 
	 int maxsize = 20;
  String sentence;
  String modifiedSentence;
  BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
  Socket clientSocket = new Socket("localhost", 6789);
  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 while(true){ 
 sentence = inFromUser.readLine();
 
 if(sentence.length() <= maxsize){
  outToServer.writeBytes(sentence + '\n');
  modifiedSentence = inFromServer.readLine();
  System.out.println("FROM SERVER: " + modifiedSentence);
  //clientSocket.close();
 }
  else System.out.println("ERROR STRING TOO LONG");
 }
}}