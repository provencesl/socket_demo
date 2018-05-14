package aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;



public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server>{
	
	
	
	private void write(AsynchronousSocketChannel asc, String response) {
		try {
			ByteBuffer buf = ByteBuffer.allocate(1024);
			buf.put(response.getBytes());
			buf.flip();
			asc.write(buf).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public void read(final AsynchronousSocketChannel asc){
		//读取数据
		ByteBuffer buf = ByteBuffer.allocate(1024);
		asc.read(buf,buf,new CompletionHandler<Integer, ByteBuffer>() {
		
		public void completed(Integer resultSize,ByteBuffer attachment){
			//进行读取之后，重置标识位
			attachment.flip();
			//获得读取的字节数
			System.out.println("server ->"+"收到客户端的数据长度为："+resultSize);
			//获得读取的数据
			String resultData = new String(attachment.array());
			System.out.println("server->"+"收到客户端的数据信息为："+resultData);
			String response = "服务器响应，收到了客户端发来的数据:"+resultData;
			write(asc,response);
			
			
		}

		@Override
		public void failed(Throwable exc, ByteBuffer attachment) {
			// TODO Auto-generated method stub
			exc.printStackTrace();
		}
		
		});
	}
	
	
	
	public void failed(Throwable exc,Server attachment){
		exc.printStackTrace();
	}
	
	public void completed(AsynchronousSocketChannel asc,Server attachment){
		
		attachment.assc.accept(attachment,this);
		read(asc);
	}
	

}
