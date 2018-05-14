package channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {

	//通过FileChannel写数据
	 public static void testFileChannelOnWrite() {
	        try {
	            RandomAccessFile accessFile = new RandomAccessFile("D://file1.txt","rw");
	            FileChannel fc = accessFile.getChannel();
	            byte[] bytes = new String("hello every one").getBytes();
	            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
	            fc.write(byteBuffer);
	            byteBuffer.clear();
	            byteBuffer.put(new String(",a good boy").getBytes());
	            byteBuffer.flip();
	            fc.write(byteBuffer);
	            fc.close();

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    //通过FileChannel读取数据
	    private static void testFileChannelOnRead() {
	        try {
	            FileChannel fileChannel = new FileInputStream(new File("D://file.txt")).getChannel();
	            //size
	            int size = 100;
				ByteBuffer byteBuffer=ByteBuffer.allocate(size );
	            int n=0;
	            while (fileChannel.read(byteBuffer) != -1) {
	                byteBuffer.flip();//缓冲区写——> 读
	                while (byteBuffer.hasRemaining()) {
	                    System.out.print((char) byteBuffer.get());
	                }
	                byteBuffer.clear();//缓冲区不会被自动覆盖，需要主动调用该方法
	            }
	            fileChannel.force(true);
	            fileChannel.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	    /*
	     * 写数据
	     * 作用：将字节从给定的可读取字节通道传输到此通道的文件
	     */
	    public static void testTransferFrom(){
	        try {
	            RandomAccessFile fromFile = new RandomAccessFile("D://file1.txt", "rw");
	            FileChannel fromChannel = fromFile.getChannel();
	            RandomAccessFile toFile = new RandomAccessFile("D://file2.txt", "rw");
	            FileChannel toChannel = toFile.getChannel();

	            long position =0;
	            long count = fromChannel.size();
	            toChannel.transferFrom(fromChannel, position, count);

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    /*
	     * 将数据从FileChannel传输到其他Channel
	     */
	    public static void testTransferTo() {
	        try {
	            RandomAccessFile fromFile = new RandomAccessFile("D://file1.txt", "rw");
	            FileChannel fromChannel = fromFile.getChannel();
	            RandomAccessFile toFile = new RandomAccessFile("D://file3.txt", "rw");
	            FileChannel toChannel = toFile.getChannel();

	            long position=0;
	            long count = fromChannel.size();
	            fromChannel.transferTo(position,count,toChannel);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    public static void main(String[] args){
	    	
	    	testFileChannelOnRead();
	    	testFileChannelOnWrite();
	    	testTransferFrom();
	    	testTransferTo();
	    }
	    
}
