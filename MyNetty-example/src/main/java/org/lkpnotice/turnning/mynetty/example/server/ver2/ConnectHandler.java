package org.lkpnotice.turnning.mynetty.example.server.ver2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.lkpnotice.turnning.mynetty.example.comm.v2.Message;
import org.lkpnotice.turnning.mynetty.example.comm.v2.MessageType;

import java.net.InetSocketAddress;

/**
 * Created by liujinpeng on 2019/3/7.
 */
public class ConnectHandler extends SimpleChannelInboundHandler<Message> {
    //增加黑名单功能
    private String[] blackIps = {"192.168.199.201"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        //如果是连接信息，判断是否是黑名单ip
        if(message != null && message.getType() == MessageType.CONNECT_REQ.getValue()){
            Message response = null;
            boolean ok = true;
            for (String ip : blackIps) {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                if(address.getHostName().equals(ip)){
                    ok = false;
                }
            }
            response = ok ? buildMessage((byte)MessageType.CONNECT_SUCCESS.getValue()):
                    buildMessage((byte) MessageType.CONNECT_FAIL.getValue());
            ctx.writeAndFlush(response);
        }else{
            ctx.fireChannelRead(message);
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

    private Message buildMessage(byte result){
        Message msg = new Message();
        msg.setType(result);
        return msg;
    }




}
