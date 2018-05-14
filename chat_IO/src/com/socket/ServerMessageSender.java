package com.socket;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerMessageSender extends Thread{

	
	private Socket socket;
	
	public ServerMessageSender(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		
		try {
			
			BufferedWriter writer = new BufferedWriter
					(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
			
			try {
				String msg = "server : welcome "+ socket.getPort();
				writer.write(msg);
				writer.newLine();
				writer.flush();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
}
