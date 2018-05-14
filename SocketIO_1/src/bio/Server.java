package bio;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	final static int PORT = 8765;
	
	public static void main(String[] args){
		
		ServerSocket server = null;
		
		try {
			server = new ServerSocket(PORT);
			System.out.println("server start..");
			System.out.println("server遇到阻塞");
			//进行阻塞
			Socket socket = server.accept();
			//新建一个线程执行客户端的任务
			new Thread(new ServerHandler(socket)).start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
