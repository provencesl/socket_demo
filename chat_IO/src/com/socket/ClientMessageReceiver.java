package com.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.xml.sax.InputSource;

public class ClientMessageReceiver extends Thread{

	private Socket socket;
	
	public ClientMessageReceiver(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		
		try {
			BufferedReader reader = new BufferedReader
					(new InputStreamReader(socket.getInputStream(),"utf-8"));
			
			String content;
			
			while(true){
				if(socket.isClosed()){
					System.out.println("Socket已关闭:无法获得信息");
					reader.close();
					socket.close();
					break;
				}
				
				content = reader.readLine();
				if(content.equals("bye")){
					System.out.println("对方请求关闭连接，无法继续进行聊天");
					reader.close();
					socket.close();
					break;
				}
				System.out.println(content + "\n");
			}
			
			reader.close();
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
}
