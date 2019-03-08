package org.lkpnotice.turnning.mynetty.example.comm.v3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * Created by liujinpeng on 2019/3/8.
 */
public class MsgPackDecode extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf msg, List<Object> out) throws Exception {
        final int length = msg.readableBytes();
        final  byte[] array = new byte[length];
        msg.getBytes(msg.readerIndex(),array,0,length);
        out.add(new MessagePack().read(array, IMMessage.class));
    }
}
