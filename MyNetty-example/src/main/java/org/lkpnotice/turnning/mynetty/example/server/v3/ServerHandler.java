package org.lkpnotice.turnning.mynetty.example.server.v3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.lkpnotice.turnning.mynetty.example.comm.v3.ApplicationContext;
import org.lkpnotice.turnning.mynetty.example.comm.v3.IMMessage;
import org.lkpnotice.turnning.mynetty.example.comm.v3.MessageType;

/**
 * Created by liujinpeng on 2019/3/7.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext ctx;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端Handler创建。。。");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
        System.out.println("有客户端连接：" + ctx.channel().remoteAddress().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel Read");


        IMMessage message = (IMMessage)msg;
        if(message.getMsgType() == MessageType.TYPE_AUTH.value()){          //认证消息
            System.out.println("认证消息：" + msg);
            ApplicationContext.add(message.getUid(),ctx);
        }else if(message.getMsgType() == MessageType.TYPE_TEXT.value()){    //CHAT消息
            ChannelHandlerContext c = ApplicationContext.getContext(message.getReceiveId());
            if(c==null){           //接收方不在线，反馈给客户端
                message.setMsg("对方不在线！");
                ctx.writeAndFlush(message);
            }else{                 //将消转发给接收方
                System.out.println("转发消息：" + msg);
                c.writeAndFlush(message);
            }
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("与客户端断开连接:"+cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
