package org.lkpnotice.turnning.mynetty.example.comm.v3;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liujinpeng on 2019/3/7.
 */
public class ApplicationContext {
    public static Map<Integer,ChannelHandlerContext> onlineUsers = new HashMap<Integer,ChannelHandlerContext>();
    public static void add(Integer uid,ChannelHandlerContext ctx){
        onlineUsers.put(uid,ctx);
    }

    public static void remove(Integer uid){
        onlineUsers.remove(uid);
    }

    public static ChannelHandlerContext getContext(Integer uid){
        return onlineUsers.get(uid);
    }

}
