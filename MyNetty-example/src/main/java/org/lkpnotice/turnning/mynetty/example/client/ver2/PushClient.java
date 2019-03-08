package org.lkpnotice.turnning.mynetty.example.client.ver2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by liujinpeng on 2019/3/7.
 */
public class PushClient {
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    //换掉ip
    private String host = "localhost";
    private int port = 8000;

    public PushClient() {
    }

    public PushClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws Exception {
        try{
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bs = new Bootstrap();
            bs.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline p = channel.pipeline();
                            p.addLast(new IdleStateHandler(20, 10, 0));
                            p.addLast(new ObjectEncoder());
                            p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            p.addLast(new ReadTimeoutHandler(20000));
                            p.addLast(new ConnectHandler());
                            p.addLast(new HeartBeatHandler());
                            p.addLast(new PushMsgHandler());
                        }
                    });
            System.out.println("开始连接");
            ChannelFuture future = bs.connect(new InetSocketAddress(host, port)).sync();
            future.channel().closeFuture().sync();//这一步会阻塞住
            System.out.println("关闭后");
        } finally {
            //断错重连
            executor.execute(new Runnable() {
                public void run() {
                    System.out.println("Client 尝试重新连接-->>>>>>");
                    //等待InterVAl时间，重连
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        //发起重连
                        connect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    public static void main(String[] args ) throws Exception {
        PushClient pushClient = new PushClient();
        pushClient.connect();
    }

}
