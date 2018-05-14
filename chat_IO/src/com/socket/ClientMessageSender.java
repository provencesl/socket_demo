package com.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientMessageSender extends Thread{

	
	private Socket socket;
	
	public ClientMessageSender(Socket socket){
		this.socket= socket;
	}
	
	
	public void run(){
		
		try {
			BufferedWriter writer = new BufferedWriter
					(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
		
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			
			String msg = null;
			for(;;){
				msg = inputReader.readLine();
				
				if(msg.toLowerCase().equals("exit")){
					System.exit(0);
				}
				if(socket.isClosed()){
					System.out.println("socket已经关闭，无法发送消息");
					writer.close();
					socket.close();
					break;
				}
				writer.write(msg);
				writer.newLine();
				writer.flush();
				System.out.println("");
				
			}
		
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
}
