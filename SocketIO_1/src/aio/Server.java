package aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	//线程池
	private ExecutorService executorService;
	//线程组
	private AsynchronousChannelGroup threadGroup;
	//服务器通道
	public AsynchronousServerSocketChannel assc;
	
	public Server(int port){
		try {
			//创建缓冲池
			executorService = Executors.newCachedThreadPool();
			//创建线程组
			threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
			//创建服务器通道
			assc = AsynchronousServerSocketChannel.open();
			//进行绑定
			assc.bind(new InetSocketAddress(port));
			
			System.out.println("server start,port:" + port);
			
			//进行阻塞
			assc.accept(this,new ServerCompletionHandler());
			
			//一直阻塞，不让服务器停止
			Thread.sleep(Integer.MAX_VALUE);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Server server = new Server(8765);
	}
	
	
	
}
