package channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelTest {

    private int size=1024;

    public void initChannel() throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        //socketChannel.socket().bind(new InetSocketAddress(9999));jdk 1.7之前
        socketChannel.bind(new InetSocketAddress(9999));
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        while (true) {
            SocketChannel channel = socketChannel.accept();
            if (channel != null) {
                InetSocketAddress remoteAddress = (InetSocketAddress) channel.getRemoteAddress();
                System.out.println(remoteAddress.getAddress());
                System.out.println(remoteAddress.getPort());
                channel.read(byteBuffer);
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    System.out.print((char) byteBuffer.get());
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            new ServerSocketChannelTest().initChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}