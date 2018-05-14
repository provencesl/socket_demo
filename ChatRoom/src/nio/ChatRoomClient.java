package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ChatRoomClient {

	
	
	 private Charset charset = Charset.forName("UTF-8");  
	    private static final int port = 5000;  
	    private Selector selector = null;  
	    private SocketChannel sc = null;  
	  
	    public void init() throws IOException {  
	        selector = Selector.open();  
	        sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));  
	        sc.configureBlocking(false);  
	        sc.register(selector, SelectionKey.OP_READ);  
	        new Thread(new ClientThread()).start();  
	        // 在主线程中 从键盘读取数据输入到服务器端  
	        Scanner scan = new Scanner(System.in);  
	        while (scan.hasNextLine()) {  
	            String line = scan.nextLine();  
	            if ("".equals(line)) {  
	                continue;  
	            }  
	            int capacity = charset.encode(line).capacity();  
	            String strCapacity = capacity + "";  
	            // result是strCapacity经过格式（-）处理之后的结果，方便服务端解析  
	            String result = null;  
	            if (strCapacity.length() == 1) {  
	                result = "---" + strCapacity;  
	            } else if (strCapacity.length() == 2) {  
	                result = "--" + strCapacity;  
	            } else if (strCapacity.length() == 3) {  
	                result = "-" + strCapacity;  
	            } else if (strCapacity.length() == 4) {  
	                result = strCapacity;  
	            }  
	            // sc既能写也能读，这边是写  
	            sc.write(charset.encode(result));  
	            sc.write(charset.encode(line));  
	  
	        }  
	  
	    }  
	  
	    private class ClientThread implements Runnable {  
	        public void run() {  
	            try {  
	                while (true) {  
	                    int readyChannels = selector.select();  
	                    if (readyChannels == 0)  
	                        continue;  
	                    Set<SelectionKey> selectedKeys = selector.selectedKeys();  
	                    Iterator<SelectionKey> keyIterator = selectedKeys  
	                            .iterator();  
	                    while (keyIterator.hasNext()) {  
	                        SelectionKey sk = (SelectionKey) keyIterator.next();  
	                        keyIterator.remove();  
	                        dealWithSelectionKey(sk);  
	                    }  
	                }  
	            } catch (IOException io) {  
	            }  
	        }  
	  
	        private void dealWithSelectionKey(SelectionKey sk) throws IOException {  
	  
	            if (sk.isReadable()) {  
	                SocketChannel sc = (SocketChannel) sk.channel();  
	                ByteBuffer buff = ByteBuffer.allocate(1024);  
	                String content = "";  
	                while (sc.read(buff) > 0) {  
	                    buff.flip();  
	                    content += charset.decode(buff);  
	                }  
	                System.out.println(content);  
	                sk.interestOps(SelectionKey.OP_READ);  
	            }  
	        }  
	    }  
	  
	    public static void main(String[] args) throws IOException {  
	        new ChatRoomClient().init();  
	    }  
	
	
	
	
	
}
