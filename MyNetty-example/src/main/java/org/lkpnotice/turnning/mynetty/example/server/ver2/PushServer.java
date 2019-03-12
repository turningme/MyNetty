package org.lkpnotice.turnning.mynetty.example.server.ver2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.lkpnotice.turnning.mynetty.example.comm.Logger;
import org.lkpnotice.turnning.mynetty.example.comm.v2.ChannelList;
import org.lkpnotice.turnning.mynetty.example.comm.v2.Message;
import org.lkpnotice.turnning.mynetty.example.comm.v2.MessageType;
import org.lkpnotice.turnning.mynetty.example.comm.v2.PushMsg;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by liujinpeng on 2019/3/7.
 */
public class PushServer {
    public void bind() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bs = new ServerBootstrap();
        bs.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 2)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline p = channel.pipeline();
                        p.addLast(new ObjectEncoder());
                        p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                        //心跳超时
                        p.addLast(new ReadTimeoutHandler(50000));
                        p.addLast(new ConnectHandler());
                        p.addLast(new HeartBeatHandler());
                    }
                });
        bs.bind(8000).sync();
        System.out.println("com.liu.nettypushtest.server 8000 start....");


        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("push消息....");

                while(true){
                    push();

                    try {
                        TimeUnit.MILLISECONDS.sleep(1000L);
                    } catch (InterruptedException e) {
                        Logger.log(e+ "");
                        e.printStackTrace();
                    }
                }


            }
        }).start();
    }


    Integer count = 0;

    //消息推送
    public void push(){
        List<Channel> channels = ChannelList.channels;
        if (channels.size() <=0) {
            System.out.println("没有客户端列表消息");
            return;
        }

        count++;
        System.out.println("push 消息 + " + channels.size());
        Message message = new Message();
        message.setType(MessageType.MSG_PUSH.getValue());

        message.setMsg(getPushMsg());
        for (Channel channel : channels){
            channel.writeAndFlush(message);
        }


    }


    public PushMsg getPushMsg(){
        PushMsg pushMsg = new PushMsg();
/*        pushMsg.setAuthor_name("中新社");
        pushMsg.setDate("2017-04-12 13:51");
        pushMsg.setThumbnail_pic_s("http:\\/\\/05.imgmini.eastday.com\\/mobile\\/20170412\\/20170412135121_ff0cae3d2601191a77afa948a8424142_1_mwpm_03200403.jpeg");
        pushMsg.setTitle("法国安娜思托保健品进军亚洲市场");
        pushMsg.setUrl("http:\\/\\/mini.eastday.com\\/mobile\\/170412135121788.html");*/

        pushMsg.setAuthor_name("中新社 - " + count);
        pushMsg.setDate("");
        pushMsg.setThumbnail_pic_s("");
        pushMsg.setTitle("");
        pushMsg.setUrl("");
        return pushMsg;
    }



    public static void main(String[] args) throws Exception{
        PushServer pushServer = new PushServer();
        pushServer.bind();
    }

}
