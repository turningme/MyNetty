package org.lkpnotice.turnning.mynetty.example.client.ver2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.ScheduledFuture;
import org.lkpnotice.turnning.mynetty.example.comm.v2.Message;
import org.lkpnotice.turnning.mynetty.example.comm.v2.MessageType;

import java.util.concurrent.TimeUnit;

/**
 * Created by liujinpeng on 2019/3/7.
 */
public class HeartBeatHandler extends SimpleChannelInboundHandler<Message> {
    private volatile ScheduledFuture<?> heartBeat;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        //如果是心跳包
        if(message != null && message.getType() == MessageType.CONNECT_SUCCESS.getValue()){
            //50秒钟发一个心跳
            heartBeat = ctx.executor().scheduleAtFixedRate(
                    new HeartBeatTask(ctx), 0, 50000, TimeUnit.MILLISECONDS);
        }else if(message != null &&
                message.getType() == MessageType.HEARTBEAT_RESP.getValue()){
            System.out.println("Client reciver heart beat message : ----> " + message);
        }else{
            //编码好的Message传递给下一个Handler
            ctx.fireChannelRead(message);
        }
    }

    private Message buildMessage(byte result){
        Message msg = new Message();
        msg.setType(result);
        return msg;
    }






    //心跳包发送任务
    private class HeartBeatTask implements Runnable{

        private ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        public void run() {
            Message message = buildHeartMessage();
            System.out.println("Client send heart beat message : ----> " + message);
            ctx.writeAndFlush(message);
        }

        private Message buildHeartMessage(){
            Message message = new Message();
            message.setType(MessageType.HEARTBEAT_REQ.getValue());
            return message;
        }
    }
}
