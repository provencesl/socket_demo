package com.socket;

import java.net.InetAddress;
import java.net.Socket;

public class Client {

	
	private static final int port = 9999;
	
	private static final String bindAddr = "127.0.0.1";
	
	public static void main(String[] args){
		
		try {
			System.out.println("正在连接socket服务器");
			Socket socket = new Socket(InetAddress.getByName(bindAddr),port);
			System.out.println("已连接==========");
			
			
			new ClientMessageSender(socket).start();
			new ClientMessageReceiver(socket).start();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
