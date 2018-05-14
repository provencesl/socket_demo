package channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

//接收方
public class DatagramChannelRecieveTest{

  private int size=1024;

  private void receiveData() throws IOException {
      DatagramChannel channel = DatagramChannel.open();
      channel.socket().bind(new InetSocketAddress(9999));
      ByteBuffer byteBuffer = ByteBuffer.allocate(size);
      byteBuffer.clear();
      SocketAddress address = channel.receive(byteBuffer);//receive data
      byteBuffer.flip();
      while (byteBuffer.hasRemaining()) {
          System.out.print((char) byteBuffer.get());
      }
  }

  public static void main(String[] args) {
      try {
          new DatagramChannelRecieveTest().receiveData();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
}