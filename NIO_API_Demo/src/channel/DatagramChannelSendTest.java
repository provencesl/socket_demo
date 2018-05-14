package channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelSendTest {

    public void sendData() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        ByteBuffer byteBuffer = ByteBuffer.wrap(new String("i 'm client").getBytes());
        int bytesSent = channel.send(byteBuffer, new InetSocketAddress("127.0.0.1", 9999));
    }

    public static void main(String[] args) {
        try {
            new DatagramChannelSendTest().sendData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}