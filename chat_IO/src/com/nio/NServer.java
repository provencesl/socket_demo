package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/*
 * 服务端为媒介，沟通两个客户端，实现NIO非阻塞
 */
public class NServer {
    private Selector selector;
    private Charset charset = Charset.forName("UTF-8");
     
    public void init() throws Exception {
        selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 3000);
        server.socket().bind(isa);
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0) {
            for (SelectionKey key : selector.selectedKeys()) {
                selector.selectedKeys().remove(key);
                if (key.isAcceptable()) {
                    SocketChannel sc = server.accept();
                    System.out.println("create new session from "+sc.getRemoteAddress()+"\n");
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    key.interestOps(SelectionKey.OP_ACCEPT);        
                    sc.write(charset.encode("welcome"+sc.getRemoteAddress()));
                }
                 
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel)key.channel();
                    ByteBuffer buff = ByteBuffer.allocate(1024);
                    String content = "";
                    try {
                        while (sc.read(buff) > 0) {
                            buff.flip();
                            content += charset.decode(buff);
                            buff.clear();
                        }                        
                        key.interestOps(SelectionKey.OP_READ);
                    } catch (IOException e) {
                        key.cancel();
                        if (key.channel() != null)
                            key.channel().close();
                    }
                    if (content.length() > 0) {
                        for (SelectionKey sk : selector.keys()) {
                            Channel targetchannel = sk.channel();
                           if (targetchannel instanceof SocketChannel && targetchannel!=sc) {
                                SocketChannel dest = (SocketChannel)targetchannel;
                                dest.write(charset.encode(content));
                            }
                        }
                    }
                }
            }
        }
    }
     
    public static void main(String[] args) throws Exception {
        new NServer().init();
    }
}

