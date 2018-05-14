package aio;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

public class Client implements Runnable{

	private AsynchronousSocketChannel asc;
	
	public Client() throws Exception{
		asc = AsynchronousSocketChannel.open();
	}
	
	public void connect(){
		asc.connect(new InetSocketAddress("127.0.0.1",8765));
	}
	
	public void read(){
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		try {
			asc.read(buf).get();
			buf.flip();
			byte[] respByte = new byte[buf.remaining()];
			buf.get(respByte);
			System.out.println(new String(respByte,"utf-8").trim());
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (ExecutionException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void write(String request){
		try {
			asc.write(ByteBuffer.wrap(request.getBytes()));
			read();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public void run(){
		while(true){
			
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		Client client1 = new Client();
		client1.connect();
		
//		Client client2 = new Client();
//		client2.connect();
		
		new Thread(client1,"c1").start();
//		new Thread(client2,"c2").start();
		
		Thread.sleep(1000);
		
		client1.write("c1");
//		client2.write("c2");
		
	}
	
	
}
