package com.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/*
 * 功能：
 * 消息接收端，额外负责信息广播
 * 
 */


/*
 * 接收消息 
 */
public class ServerMessageReceiver implements Runnable{

	private Socket socket;
	//socket字典列表
	private List<Socket> nodes = new ArrayList<>();
	
	
	public ServerMessageReceiver(Socket sc,List<Socket> nodes){
		this.socket = sc;
		this.nodes = nodes;
	}
	
	/*
	 * 消息广播到其他节点
	 */
	public void run(){
		
		try {
			BufferedReader reader = new BufferedReader
					(new InputStreamReader(socket.getInputStream(),"utf-8"));
			
			//接收到的消息
			String content;
			
			while(true){
				if(socket.isClosed()){
					System.out.println("socket已经关闭，无法获得信息");
					reader.close();
					socket.close();
					break;
					
				}
				
				content = reader.readLine();
				
				if(content != null && content.equals("bye")){
					System.out.println("对方请求关闭连接，无法继续进行聊天");
					reader.close();
					socket.close();
					break;
				}

				String message = socket.getPort() + ":" +content;
				
				//广播消息
				for(Socket n : this.nodes){
					
					if(n != this.socket){
						BufferedWriter writer = new BufferedWriter
								(new OutputStreamWriter(n.getOutputStream(),"utf-8"));
						
						writer.write(message);
						writer.newLine();
						writer.flush();
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
