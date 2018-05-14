package channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelTest {
    private int size=1024;

    public void connectServer() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        ByteBuffer byteBuffer=ByteBuffer.allocate(size);
        byteBuffer.put(new String("hello server").getBytes());
        byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }
    }

    public static void main(String[] args) throws IOException {
        new SocketChannelTest().connectServer();
    }
}