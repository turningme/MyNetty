package org.lkpnotice.turnning.mynetty.example.server.ver2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.lkpnotice.turnning.mynetty.example.comm.v2.ChannelList;
import org.lkpnotice.turnning.mynetty.example.comm.v2.Message;
import org.lkpnotice.turnning.mynetty.example.comm.v2.MessageType;

/**
 * Created by liujinpeng on 2019/3/7.
 */
public class HeartBeatHandler extends SimpleChannelInboundHandler<Message> {
    //加入到在线列表，只有在线用户才可以实时推送
    @Override
    public void channelActive(ChannelHandlerContext ctx)  {
        ChannelList.channels.add(ctx.channel());
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        //如果是心跳包ping，则返回pong
        if(message != null && message.getType() == MessageType.HEARTBEAT_REQ.getValue()){
            Message response = buildMessage(MessageType.HEARTBEAT_RESP.getValue());
            ctx.writeAndFlush(response);
        }else{
            ctx.fireChannelRead(message);
        }
    }


    private Message buildMessage(byte result){
        Message msg = new Message();
        msg.setType(result);
        return msg;
    }




}
